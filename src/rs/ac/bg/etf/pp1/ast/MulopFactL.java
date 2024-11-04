// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class MulopFactL extends MulopFactorList {

    private MulopFactorList MulopFactorList;
    private MulopFactor MulopFactor;

    public MulopFactL (MulopFactorList MulopFactorList, MulopFactor MulopFactor) {
        this.MulopFactorList=MulopFactorList;
        if(MulopFactorList!=null) MulopFactorList.setParent(this);
        this.MulopFactor=MulopFactor;
        if(MulopFactor!=null) MulopFactor.setParent(this);
    }

    public MulopFactorList getMulopFactorList() {
        return MulopFactorList;
    }

    public void setMulopFactorList(MulopFactorList MulopFactorList) {
        this.MulopFactorList=MulopFactorList;
    }

    public MulopFactor getMulopFactor() {
        return MulopFactor;
    }

    public void setMulopFactor(MulopFactor MulopFactor) {
        this.MulopFactor=MulopFactor;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(MulopFactorList!=null) MulopFactorList.accept(visitor);
        if(MulopFactor!=null) MulopFactor.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(MulopFactorList!=null) MulopFactorList.traverseTopDown(visitor);
        if(MulopFactor!=null) MulopFactor.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(MulopFactorList!=null) MulopFactorList.traverseBottomUp(visitor);
        if(MulopFactor!=null) MulopFactor.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("MulopFactL(\n");

        if(MulopFactorList!=null)
            buffer.append(MulopFactorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(MulopFactor!=null)
            buffer.append(MulopFactor.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [MulopFactL]");
        return buffer.toString();
    }
}
