// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class IdentValueList extends IdentValList {

    private IdentValList IdentValList;
    private IdentVal IdentVal;

    public IdentValueList (IdentValList IdentValList, IdentVal IdentVal) {
        this.IdentValList=IdentValList;
        if(IdentValList!=null) IdentValList.setParent(this);
        this.IdentVal=IdentVal;
        if(IdentVal!=null) IdentVal.setParent(this);
    }

    public IdentValList getIdentValList() {
        return IdentValList;
    }

    public void setIdentValList(IdentValList IdentValList) {
        this.IdentValList=IdentValList;
    }

    public IdentVal getIdentVal() {
        return IdentVal;
    }

    public void setIdentVal(IdentVal IdentVal) {
        this.IdentVal=IdentVal;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(IdentValList!=null) IdentValList.accept(visitor);
        if(IdentVal!=null) IdentVal.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentValList!=null) IdentValList.traverseTopDown(visitor);
        if(IdentVal!=null) IdentVal.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentValList!=null) IdentValList.traverseBottomUp(visitor);
        if(IdentVal!=null) IdentVal.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentValueList(\n");

        if(IdentValList!=null)
            buffer.append(IdentValList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdentVal!=null)
            buffer.append(IdentVal.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentValueList]");
        return buffer.toString();
    }
}
