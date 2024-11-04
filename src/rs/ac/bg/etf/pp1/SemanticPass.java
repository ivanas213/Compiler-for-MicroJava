package rs.ac.bg.etf.pp1;
import org.apache.log4j.Logger;

import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.*;
import rs.etf.pp1.symboltable.concepts.*;
import rs.ac.bg.etf.pp1.ast.VisitorAdaptor;

public class SemanticPass extends VisitorAdaptor {
    int printCallCount = 0;
    int varDeclCount = 0;
    Obj currentMethod = null;
    boolean returnFound = false;
    boolean errorDetected = false;
    boolean array;
    boolean num;
    int nVars=0;
    boolean elem=false;
    Struct currentConstDeclType=null;
    Struct currentVarDeclType=null;
    Struct currentMethodType=null;
    Struct currentFormParType=null;
    String currentVarName="";
    String currentMethodName="";
    String currentFormParName="";
    Struct arrayIntType, arrayBoolType, arrayCharType,boolType;
    //za tip vrednosti konstante, radi provere da li se poklapa sa tipom konstante
    //0 za NUMBER, 1 za CHAR, 2 za BOOL
    int constValueType=0;
    int constValue=0;
    Logger log = Logger.getLogger(getClass());
    int formParamsCount=0;
    Obj f=null;
    Obj currentExprType=null;
    Obj firstDes=null;
    boolean error=false;
    boolean exprNotNumber=false;
    boolean factorNotNumber=false;
    int constCount=0;
    int localCount=0;
    int globalCount=0;
    boolean bracks=false;
    int level=0;
    public void report_error(String message, SyntaxNode info) {
        errorDetected = true;
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.error(msg.toString());
    }

    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.info(msg.toString());
    }


    public void visit(ProgName progName){
        progName.obj = Tab.insert(Obj.Prog, progName.getProgName(), Tab.noType);
        Tab.openScope();
        boolType = new Struct(Struct.Bool);
        arrayIntType=new Struct(Struct.Array,Tab.intType);
        arrayCharType=new Struct(Struct.Array, Tab.charType);
        arrayBoolType=new Struct(Struct.Array,boolType);
        //Tab.currentScope().addToLocals(new Obj(Obj.Type,"no_type",Tab.noType));
        Tab.insert(Obj.Type, "bool", boolType);
        Tab.insert(Obj.Type, "int_array", arrayIntType);
        Tab.insert(Obj.Type, "char_array", arrayCharType);
        Tab.insert(Obj.Type, "bool_array", arrayBoolType);

    }

    public void visit(Program program){
        nVars = Tab.currentScope.getnVars();
        Obj o=Tab.find("main");
        if(o==Tab.noObj || o.getKind()!=Obj.Meth){
            report_error("Greska:Program ne sadrzi funciju main",program);
        }
        // report_info(""+Code.dataSize,program);
        Tab.chainLocalSymbols(program.getProgName().obj);
        Tab.closeScope();
    }


    public void visit(Type type){
        Obj typeNode = Tab.find(type.getTypeName());
        if(typeNode == Tab.noObj){
            report_error("Nije pronadjen tip " + type.getTypeName() + " u tabeli simbola! ", null);
            type.struct = Tab.noType;
        }else{
            if(Obj.Type == typeNode.getKind()){
                type.struct = typeNode.getType();
            }else{
                report_error("Greska: Ime " + type.getTypeName() + " ne predstavlja tip!", type);
                type.struct = Tab.noType;
            }
        }
    }
    public void visit(ConstType constType){
      currentConstDeclType=constType.getType().struct;
    }

    public void visit(IdentVal identVal){
        if(Tab.find(identVal.getIdentName())!=Tab.noObj){
            report_error("Greska: Konstanta se ne moze dvostruko definisati!",identVal);
        }
        else
        if((currentConstDeclType==boolType && constValueType!=2) || (currentConstDeclType==Tab.intType && constValueType!=0) || (currentConstDeclType==Tab.charType && constValueType!=1)){
            report_error("Greska: Vrednost konstante ne odgovara tipu konstante",identVal);
        }
        else{
            identVal.obj = Tab.insert(Obj.Con, identVal.getIdentName(), currentConstDeclType);
            identVal.obj.setAdr(identVal.getConst().obj.getAdr());
            if(currentConstDeclType==boolType){

            }
        }


    }
    public void visit(VarType varType){
        currentVarDeclType=varType.getType().struct;
        varType.struct=varType.getType().struct;
        //report_info("Postavljen je tip varijable na "+currentVarDeclType.getKind(),varType);
    }

    public void visit(Ident ident){
        ident.obj=new Obj(Obj.Var,ident.getVarIdent(),currentVarDeclType);
        currentVarName=ident.getVarIdent();
       // report_info("Postavljeno je ime varijable na "+currentVarName,ident);
        if(bracks) {
            if (level==0) {
                if (Tab.find(currentVarName) != Tab.noObj) {
                    report_error("Greska:Dvostruka deklaracija varijable " + currentVarName, ident);
                } else {
                    Struct s;
                    if (currentVarDeclType == Tab.charType) s = arrayCharType;
                    else if (currentVarDeclType == Tab.intType) s = arrayIntType;
                    else s = arrayBoolType;
                    //   globalCount++;
                    Tab.insert(Obj.Var, currentVarName, s);
                    //nVars++;
                  //  report_info("Dodata je globalna varijabla " + currentVarName + " " + s.getKind(), ident);
                }
            } else {
                if (Tab.currentScope().getLocals()!=null &&Tab.currentScope().getLocals().searchKey(currentVarName) != null) {
                    report_error("Greska:Dvostruka deklaracija varijable " + currentVarName, ident);
                } else {
                    //localCount++;
                    Struct s;
                    if (currentVarDeclType == Tab.charType) s = arrayCharType;
                    else if (currentVarDeclType == Tab.intType) s = arrayIntType;
                    else s = arrayBoolType;
                    Tab.insert(Obj.Var, currentVarName, s);
                   // report_info("Dodata je lokalna varijabla " + currentVarName + " " + s.getKind(), ident);

                }
            }
        }
        else {
            if(level==0){
                if(Tab.find(currentVarName)!=Tab.noObj){
                    report_error("Greska:Dvostruka deklaracija varijable "+currentVarName,ident);
                }
                else {
                    // globalCount++;
                   // report_info("Dodata je globalna varijabla "+currentVarName+" "+currentVarDeclType.getKind(),ident);

                    Tab.insert(Obj.Var, currentVarName, currentVarDeclType);
                }
            }
            else if(Tab.currentScope().getLocals()!=null &&Tab.currentScope().getLocals().searchKey(currentVarName)!=null){
                report_error("Greska:Dvostruka deklaracija varijable "+currentVarName,ident);
            }
            else{
             //   report_info("Dodata je lokalna varijabla "+currentVarName+" "+currentVarDeclType.getKind(),ident);
                Tab.insert(Obj.Var,currentVarName, currentVarDeclType);
            }
        }
    }
    public void visit(Brackets brackets){
        bracks=true;


        //if(currentVarDeclType.equals("bool")) s=Tab.currentScope().getLocals().searchKey("bool_array");
       // Tab.insert(Obj.Var,currentVarName,s);
    }
    public void visit(NoBrackets noBrackets){
        bracks=false;


    }
    public void visit(MethodDecl methodDecl){
        if(!returnFound && currentMethodType != Tab.noType){
            report_error("Semanticka greska na liniji " + methodDecl.getLine() + ": funkcija " + currentMethodName + " nema return iskaz!", methodDecl);
        }
        currentMethod.setLevel(formParamsCount);
        Tab.chainLocalSymbols(currentMethod);
        Tab.closeScope();
        level--;
        returnFound = false;
        currentMethod = null;
    }
    public void visit(ReturnStatement returnStatement){
        returnFound=true;
    }
    public void visit(ReturnExprStatement returnExprStatement){
        returnFound=true;
        returnFound=true;
    }
    public void visit(MethodNoVoid methodNoVoid){
        currentMethodType=methodNoVoid.getType().struct;
    }
    public void visit(MethodVoid methodVoid){
        currentMethodType=Tab.noType;
    }
    public void visit(MethodName methodName){
        returnFound=false;
        currentMethodName=methodName.getMethodName();
        currentMethod=methodName.obj=Tab.insert(Obj.Meth,currentMethodName,currentMethodType);
        //report_info("Dodat je metod "+currentMethodName+" "+currentMethodType.getKind(),methodName);
        level++;
        Tab.openScope();
    }

    public void visit(TypeFormPars typeFormPars){
      currentFormParType=typeFormPars.getType().struct;
    }

    public void visit(IdentFormPars identFormPars){
        currentFormParName=identFormPars.getFormParName();
    }

    public void visit(NumConstant numConstant){
        constValueType=0;
        constValue=numConstant.getN1();
        numConstant.obj=new Obj(Obj.Var,"",Tab.noType);
        numConstant.obj.setAdr(numConstant.getN1());
    }
    public void visit(CharConstant charConstant){
        constValueType=1;
        constValue=charConstant.getC1();
        charConstant.obj=new Obj(Obj.Var,"",Tab.noType);
        charConstant.obj.setAdr(charConstant.getC1());
    }
    public void visit(BoolConstant boolConstant){
        constValueType=2;
        constValue=boolConstant.getB1()?1:0;
        boolConstant.obj=new Obj(Obj.Var,"",Tab.noType);
        boolConstant.obj.setAdr(boolConstant.getB1()?1:0);
    }
    public void visit(WithoutBracks withoutBracks){
        if(Tab.currentScope().getLocals().searchKey(currentFormParName)!=null){
            report_error("Greska: Dvostruka definicija formalnog parametra",withoutBracks);
        }
        else{
            formParamsCount++;
            Tab.insert(Obj.Var,currentFormParName,currentFormParType);
        }

    }
    public void visit(WithBracks withBracks){
        if(Tab.currentScope().getLocals().searchKey(currentFormParName)!=null){
            report_error("Greska: Dvostruka definicija formalnog parametra",withBracks);
        }
        else{
            Struct s;
            if(currentVarDeclType==Tab.charType) s=arrayCharType;
            else if(currentVarDeclType==Tab.intType) s=arrayIntType;
            else s=arrayBoolType;
            formParamsCount++;
            Tab.insert(Obj.Var,currentFormParName,s);
        }
    }

    public void visit(FormParameters formParameters){
        formParamsCount=0;
    }

    public void visit(VarTypeLocal varTypeLocal){
        currentVarDeclType=varTypeLocal.getVarType().struct;
        varTypeLocal.struct=varTypeLocal.getVarType().struct;
        //report_info("Postavljen je tip lokalne varijable na "+currentVarDeclType.getKind(),varTypeLocal);

    }

    public void visit(NoIdentOrExprList noIdentOrExprList){
        firstDes=f;
        elem=false;
        f=noIdentOrExprList.obj=Tab.find(noIdentOrExprList.getI1());
        report_info("Upotreba "+f.getName()+" ",noIdentOrExprList);
        //report_info("Pronadjeno je "+noIdentOrExprList.getI1()+" "+f.getAdr(),noIdentOrExprList);
        //noIdentOrExprList.obj=new Object(noIdentOrExprList.getI1())
        if(f==null){
           report_error("Greska: Unet je nepostojeci tip",noIdentOrExprList);
        }
        else{
            //noIdentOrExprList.obj=new Obj(f.getKind(),)
            if(f.getType()==arrayIntType || f.getType()==arrayBoolType || f.getType()==arrayCharType) array=true;
            if(f.getKind()==Obj.Var){
                if(f.getLevel()!=1){
                    localCount++;
                }
                else globalCount++;
            }
            else if(f.getKind()==Obj.Con){
                constCount++;
            }
           // report_info("Prvi deo designatora "+noIdentOrExprList.getI1(),noIdentOrExprList);
        }

    }
    public void visit(IdentOrExprL identOrExprL){
        Struct type=identOrExprL.getDesignator().obj.getType();
       //Struct type=f.getType();
        if(!num){
            report_error("Greska: expr tip nije int",identOrExprL);
        }
        if(!array){
            report_error("Greska u tipovima",identOrExprL);
        }
        elem=true;
        num=false;
        array=false;
        if(type!=arrayCharType && type!=arrayBoolType && type!=arrayIntType){
            report_error("Greska: Tipovi nisu kompatibilni 1",identOrExprL);
        }
        identOrExprL.obj=new Obj(Obj.Elem," ",type.getElemType());
        //report_info(""+type.getElemType().getKind(),identOrExprL);
        //report_info("Deo designatora "+identOrExprL.getDesignator().obj.getType().getKind(),identOrExprL);
    }



    public void visit(DesignatorExpr designatorExpr){
        designatorExpr.struct=designatorExpr.getExpr().struct;
        if(currentExprType.getType()==arrayBoolType){
            if(designatorExpr.struct!=boolType){
                report_error("Greska: Tipovi nisu kompatibilni 2",designatorExpr);
            }
        }
        else  if(currentExprType.getType()==arrayIntType){
            if(designatorExpr.struct!=Tab.intType){
                report_error("Greska: Tipovi nisu kompatibilni 3",designatorExpr);
            }
        }
        else  if(currentExprType.getType()==arrayCharType){
            if(designatorExpr.struct!=Tab.charType){
                report_error("Greska: Tipovi nisu kompatibilni 4",designatorExpr);
            }
        }
        else if(designatorExpr.struct!=currentExprType.getType()){
            report_error("Greska: Tipovi nisu kompatibilni 5 "+designatorExpr.struct.getKind()+" "+currentExprType.getType().getKind(),designatorExpr);

        }
      //  else if (designatorExpr.struct.getKind()!=Obj.Var){
       //     report_error("Greska: Nije odgovarajuci tip leve oznake"+f.getType().getKind(),designatorExpr);
      //  }
    }

    public void visit(DesignatorInc designatorInc){
        if (f.getType()!=Tab.intType){
            report_error("Greska: Tip nije int ",designatorInc);
        }
        if (f.getKind()!=Obj.Var){
            report_error("Greska: Nije odgovarajuci tip leve oznake",designatorInc);
        }
    }
    public void visit(DesignatorDec designatorDec){
        if (f.getType()!=Tab.intType){
            report_error("Greska: Tip nije int ",designatorDec);
        }
        if (f.getKind()!=Obj.Var){
            report_error("Greska: Nije odgovarajuci tip leve oznake",designatorDec);
        }
    }

    public void visit(FindAnyStatement findAnyStatement){
        //findAnyStatement.ge;
        Struct type1=findAnyStatement.getDesignator().obj.getType();
        Struct type2=findAnyStatement.getDesignator1().obj.getType();
        //findAnyStatement.getFindAnyRightDesignator()
        if(type2!=arrayBoolType && type2!=arrayCharType && type2!=arrayIntType){
            report_error("Greska: Sa desne strane nije niz! "+type2.getKind(),findAnyStatement);
        }
        if(type1!=boolType){
            report_error("Greska: Sa leve strane nije bool "+type1.getKind(),findAnyStatement);
        }
    }
    public void visit(IdentOrExprExpr identOrExprExpr){
        array=true;
        if(identOrExprExpr.getExpr().struct==Tab.intType || identOrExprExpr.getExpr().struct==arrayIntType){
            num=true;
        }
        else{
            num=false;
        }
       /* if(f.getType()!=arrayBoolType && f.getType()!=arrayCharType && f.getType()!=arrayIntType){
            report_error("Greska u tipovima "+f.getType().getKind(),identOrExprExpr);
        }
        else */
        if(identOrExprExpr.getExpr().struct!=Tab.intType && identOrExprExpr.getExpr().struct!=arrayIntType){
            report_error("Greska: Indeks niza mora biti tipa int",identOrExprExpr);

        }
    }

    public void visit(ReadStatement readStatement){
        if(f.getType()!=Tab.intType && f.getType()!=Tab.charType && f.getType()!=boolType){
            report_error("Greska: Read statement",readStatement);
        }
        if (f.getKind()!=Obj.Var){
            report_error("Greska: Nije odgovarajuci tip oznake",readStatement);
        }
    }
    public void visit(PrintStatement printStatement){
        Struct f=printStatement.getExpr().struct;
        if(!elem &&(f!=Tab.intType && f!=Tab.charType && f!=boolType)){
           report_error("Greska: Nije odgovarajuci tip za print 1 "+f.getKind(),printStatement);
        }
        //else if(elem && (f!=arrayIntType && f!=arrayBoolType && f!=arrayCharType)){
         //   report_error("Greska: Nije odgovarajuci tip za print 2 "+f.getKind(),printStatement);
       // }
        //else
           // report_info("Print",printStatement);
    }

    public void visit(Expr expr){
        expr.struct=expr.getAddopTermList().struct;
        //if(expr.struct!=Tab.intType && expr.struct!=arrayIntType){
            //report_error("Greska: Expr tip nije broj! "+expr.struct.getKind(),expr);
        //}
        exprNotNumber=false;
    }

    public void visit(AddopTerm addopTerm){
        if(addopTerm.getTerm().obj.getType()!=Tab.intType){
            exprNotNumber=true;
        }
        addopTerm.struct=addopTerm.getTerm().obj.getType();
       // report_info("OVO "+addopTerm.getAddop().obj.getName(),addopTerm);

    }
    public void visit(OneAddopTerm oneAddopTerm){
        if(oneAddopTerm.getTerm().obj.getType()!=Tab.intType){
            if(oneAddopTerm.getParent().getClass()==AddopTerminalList.class){
                report_error("Greska: Tip nije int",oneAddopTerm);
            }
            exprNotNumber=true;
        }
        oneAddopTerm.struct=oneAddopTerm.getTerm().obj.getType();


    }
    public void visit(OneNegativeAddopTerm oneNegativeAddopTerm){
        if(oneNegativeAddopTerm.getTerm().obj.getType()!=Tab.intType){
            exprNotNumber=true;
            if(oneNegativeAddopTerm.getParent().getClass()==AddopTerminalList.class){
                report_error("Greska: Tip nije int",oneNegativeAddopTerm);
            }
        }
        oneNegativeAddopTerm.struct=oneNegativeAddopTerm.getTerm().obj.getType();

    }

    public void visit(OneMulopFactor oneMulopFactor){
        currentExprType=oneMulopFactor.getFactor().obj;
        if(oneMulopFactor.getFactor().obj.getType()!=Tab.intType){
            factorNotNumber=true;
        }
        oneMulopFactor.struct=oneMulopFactor.getFactor().obj.getType();
        //report_info("Postavljen mulop factor tip "+oneMulopFactor.struct.getKind(),oneMulopFactor);
        if(oneMulopFactor.getParent().getClass()==MulopFactL.class){
            if(oneMulopFactor.struct!=Tab.intType && oneMulopFactor.struct!=arrayIntType){
                report_error("Greska: Tip nije int",oneMulopFactor);
            }
        }
    }

    public void visit(MulopFactL mulopFactL){
        if(mulopFactL.getMulopFactor().obj.getType()!=Tab.intType && mulopFactL.getMulopFactor().obj.getType()!=arrayIntType){
            report_error("Greska: Tip nije int",mulopFactL);
            mulopFactL.struct=Tab.noType;

        }
        else{
            mulopFactL.struct=Tab.intType;
            //report_info("MulopFactL tip "+mulopFactL.struct.getKind(),mulopFactL);

        }
    }


    public void visit(MulopFactor mulopFactor){
        if(mulopFactor.getFactor().obj.getType()!=Tab.intType && mulopFactor.getFactor().obj.getType()!=arrayIntType){
            report_error("Greska: Faktor nije broj",mulopFactor);
            factorNotNumber=true;
            error=true;
        }
        mulopFactor.obj=mulopFactor.getFactor().obj;
    }

    public void visit(Term term){
        if(term.getMulopFactorList().struct!=null)
        term.obj=new Obj(Obj.Var,"",term.getMulopFactorList().struct);
       // if (term.obj.getType()!=Tab.intType && term.obj.getType()!=arrayIntType)
       // report_error("Greska: Nisu svi faktori izraza int "+term.obj.getType().getKind(),term);


        factorNotNumber=false;
    }

    public void visit(FactorNew factorNew){
        if(currentExprType.getType()!=Tab.intType){
            report_error("Greska: Broj argumanata niza treba da bude int",factorNew);
        }
        else {
            //report_info("New",factorNew);
            factorNew.obj=new Obj(Obj.Var," ",factorNew.getType().struct);
        }
    }
    public boolean passed(){
        return !errorDetected;
    }

    public void visit(FactorNumConst factorNumConst){
        factorNumConst.obj=new Obj(Obj.Var," ",Tab.intType);
    }

    public void visit(FactorDesignator factorDesignator){
        factorDesignator.obj=factorDesignator.getDesignator().obj;
        //report_info("Upotreba "+factorDesignator.obj.getName()+" "+factorDesignator.obj,factorDesignator);

        // factorDesignator.obj=new Obj(Obj.Var," ",factorDesignator.getDesignator().obj.getType());
        //report_info("Postavljen factor designator "+factorDesignator.obj.getName(),factorDesignator);
    }

    public void visit(FactorCharConst factorCharConst){
        factorCharConst.obj=new Obj(Obj.Var, " ",Tab.charType );
    }
    public void visit(FactorBoolConst factorBoolConst){
        factorBoolConst.obj=new Obj(Obj.Var, "",boolType);
    }
    public void visit(FactorExpr factorExpr){
        factorExpr.obj=new Obj(Obj.Var, "", factorExpr.getExpr().struct);
    }

    public void visit(AddopTerminalList addopTerminalList){
        if(addopTerminalList.getAddopTerm().struct!=Tab.intType){
            //report_error("Greska: Tip nije int",addopTerminalList);
        }
        else
        if(addopTerminalList.getAddopTermList().struct!=null && addopTerminalList.getAddopTermList().struct==Tab.intType){
            addopTerminalList.struct=Tab.intType;
        }
        else{
            addopTerminalList.struct=Tab.noType;
        }
    }

    public void visit(DesignatorExpress designatorExpress){
        //report_info("Designator express" +designatorExpress.getDesignator().obj.getType().getKind() ,designatorExpress);
    }
    public void visit(AddopPlus addopPlus){
        addopPlus.obj=new Obj(Obj.Var,addopPlus.getP1(),Tab.noType);
    }
    public void visit(AddopMinus addopMinus){
        addopMinus.obj=new Obj(Obj.Var,addopMinus.getM1(),Tab.noType);
    }
    public void visit(MulopMul mulopMul){
        mulopMul.obj=new Obj(Obj.Var,"*",Tab.noType);
    }
    public void visit(MulopDiv mulopDiv){
        mulopDiv.obj=new Obj(Obj.Var,"/",Tab.noType);
    }
    public void visit(MulopPercent mulopPercent){
        mulopPercent.obj=new Obj(Obj.Var,"%",Tab.noType);
    }
    SemanticPass(){

    }

}

