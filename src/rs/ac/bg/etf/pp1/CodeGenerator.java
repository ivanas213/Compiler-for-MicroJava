package rs.ac.bg.etf.pp1;

import org.apache.log4j.Logger;
import org.w3c.dom.css.Counter;
import rs.ac.bg.etf.pp1.ast.*;
import rs.etf.pp1.mj.runtime.Code;
import rs.etf.pp1.symboltable.Tab;
import rs.etf.pp1.symboltable.concepts.Obj;
import rs.ac.bg.etf.pp1.CounterVisitor.VarCounter;
import rs.etf.pp1.symboltable.concepts.Struct;

public class CodeGenerator extends VisitorAdaptor {

    private int mainPC;
    private Obj len;
    private Obj chr;
    private Obj ord;
    public static Obj counter=null;
    private Obj currentExpr;
    boolean element=false;
    private int exprRes=0;
    private int factorRes=0;
    private int mulopFactorRes;
    public static Obj arg=null;
    Logger log = Logger.getLogger(getClass());

    public int getMainPC() {
        return mainPC;
    }
    public void report_info(String message, SyntaxNode info) {
        StringBuilder msg = new StringBuilder(message);
        int line = (info == null) ? 0: info.getLine();
        if (line != 0)
            msg.append (" na liniji ").append(line);
        log.info(msg.toString());
    }
   public void visit(MulopFactor mulopFactor){
        //Code.load(mulopFactor.obj);
        if(mulopFactor.getMulop().obj.getName().equals("*")){
            Code.put(Code.mul);
        }
        else if(mulopFactor.getMulop().obj.getName().equals("/")){
            Code.put(Code.div);
        }
        else{
            Code.put(Code.rem);
        }
   }
   public void visit(AddopTerm addopTerm){

        if(addopTerm.getAddop().obj.getName().equals("+")){
           Code.put(Code.add);
        }
        else {
           Code.put(Code.sub);
        }
   }

   public  void visit(FactorNumConst factorNumConst){
        Code.loadConst(factorNumConst.getN1());
   }

   public void visit(DesignatorExpress designatorExpress){

        Code.store(designatorExpress.getDesignator().obj);
   }
   public void visit(FactorCharConst factorCharConst){
        Code.loadConst(factorCharConst.getC1());
   }
   public void visit(FactorBoolConst factorBoolConst){
        Code.loadConst(factorBoolConst.getB1()?1:0);
   }
   public void visit(PrintStatement printStatement){
        if(printStatement.getExpr().struct== Tab.charType){
            if(printStatement.getWide()!=null) Code.loadConst(1);
            Code.put(Code.bprint);
        }
        else{
            if(printStatement.getWide()!=null) Code.loadConst(5);
            Code.put(Code.print);

        }
   }
   public void visit(YesWide yesWide){
        yesWide.obj=new Obj(Obj.Var,"",Tab.noType);
   }
   public void visit(MethodDecl methodDecl){
        Code.put(Code.exit);
        Code.put(Code.return_);
   }
   public void visit(ReturnExprStatement returnExprStatement){
       Code.put(Code.exit);
       Code.put(Code.return_);
   }
   public void visit(ReturnStatement returnStatement){
       Code.put(Code.exit);
       Code.put(Code.return_);
   }
   public void visit(FactorDesignator factorDesignator){
        Code.load(factorDesignator.obj);
   }
   public void visit(MethodName methodName){
       if("main".equalsIgnoreCase(methodName.getMethodName())){
           mainPC=Code.pc;
       }
       methodName.obj.setAdr(Code.pc);
       // Collect arguments and local variables
       SyntaxNode methodNode = methodName.getParent();

       VarCounter varCnt = new CounterVisitor.VarCounter();
       methodNode.traverseTopDown(varCnt);

       CounterVisitor.FormParamCounter fpCnt = new CounterVisitor.FormParamCounter();
       methodNode.traverseTopDown(fpCnt);

       // Generate the entry
       Code.put(Code.enter);
       Code.put(fpCnt.getCount());
       Code.put(fpCnt.getCount() + varCnt.getCount());
   }
   public void visit(DesignatorInc designatorInc){
        Code.load(designatorInc.getDesignator().obj);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(designatorInc.getDesignator().obj);
   }
   public void visit(DesignatorDec designatorDec){
       Code.load(designatorDec.getDesignator().obj);
       Code.loadConst(-1);
        Code.put(Code.add);
        Code.store(designatorDec.getDesignator().obj);
   }
   public void visit(IdentOrExprL identOrExprL){

   }
   public void visit(IdentOrExprExpr identOrExprExpr){
       // Code.put(identOrExprExpr.getExpr().)
     //   Code.put(Code.aload);
   }
   public void visit(FactorNew factorNew){

        Code.put(Code.newarray);
       if(factorNew.getType().struct!=Tab.charType){
           Code.loadConst(1);
       }
       else
           Code.loadConst(0);
   }
   public void visit(NoIdentOrExprList noIdentOrExprList){
        if(noIdentOrExprList.obj.getType().getKind()== Struct.Array && noIdentOrExprList.getParent().getClass()==IdentOrExprL.class)
        Code.load(noIdentOrExprList.obj);
        currentExpr=noIdentOrExprList.obj;
   }
   public void visit(ReadStatement readStatement){
        Code.put(Code.read);
        Code.store(readStatement.getDesignator().obj);
   }
   public void visit(OneNegativeAddopTerm oneNegativeAddopTerm){
        Code.loadConst(-1);
        Code.put(Code.mul);
       // currentExpr=oneNegativeAddopTerm.getTerm().obj;
   }
   CodeGenerator() {
       //report_info(""+Code.dataSize,null);
       // counter=new Obj(Obj.Var,"counter",Tab.intType);
       // counter.setAdr(Code.dataSize++);
       // counter.setLevel(0);
       // arg=new Obj(Obj.Var,"arg",Tab.intType);
       // arg.setAdr(Code.dataSize++);
       //  arg.setLevel(0);
       //  Code.dataSize++;
        counter=new Obj(Obj.Var,"",Tab.intType);
       // counter.setAdr(Code.dataSize++);
        counter.setAdr(20);
        arg=new Obj(Obj.Var,"",Tab.intType);
       // arg.setAdr(Code.dataSize++);
        arg.setAdr(25);
        len=Tab.find("len");
        len.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.arraylength);
        Code.put(Code.exit);
        Code.put(Code.return_);
        chr=Tab.find("chr");
        chr.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);
        ord=Tab.find("ord");
        ord.setAdr(Code.pc);
        Code.put(Code.enter);
        Code.put(1);
        Code.put(1);
        Code.put(Code.load_n);
        Code.put(Code.exit);
        Code.put(Code.return_);

   }
   public void visit(FindAnyStatement findAnyStatement){

        Code.store(arg);
        Code.loadConst(0);
        Code.store(findAnyStatement.getDesignator().obj);
        Code.loadConst(0);
        Code.store(counter);

        int startPc=Code.pc;
        Code.load(counter);
        Code.load(findAnyStatement.getDesignator1().obj);
        Code.put(Code.arraylength);

        Code.putFalseJump(Code.ne,Code.pc);
        int adr1=Code.pc-2;
        Code.load(arg);
        Code.load(findAnyStatement.getDesignator1().obj);
        Code.load(counter);
        Code.put(Code.aload);
        Code.putFalseJump(Code.eq,Code.pc);
        int adr2=Code.pc-2;
        Code.loadConst(1);
        Code.store(findAnyStatement.getDesignator().obj);
        Code.fixup(adr2);
        Code.load(counter);
        Code.loadConst(1);
        Code.put(Code.add);
        Code.store(counter);
        Code.putJump(startPc);
        Code.fixup(adr1);

   }



}
