// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class DesignList extends DesignatorList {

    private DesignatorList DesignatorList;
    private DesignatorOrNo DesignatorOrNo;

    public DesignList (DesignatorList DesignatorList, DesignatorOrNo DesignatorOrNo) {
        this.DesignatorList=DesignatorList;
        if(DesignatorList!=null) DesignatorList.setParent(this);
        this.DesignatorOrNo=DesignatorOrNo;
        if(DesignatorOrNo!=null) DesignatorOrNo.setParent(this);
    }

    public DesignatorList getDesignatorList() {
        return DesignatorList;
    }

    public void setDesignatorList(DesignatorList DesignatorList) {
        this.DesignatorList=DesignatorList;
    }

    public DesignatorOrNo getDesignatorOrNo() {
        return DesignatorOrNo;
    }

    public void setDesignatorOrNo(DesignatorOrNo DesignatorOrNo) {
        this.DesignatorOrNo=DesignatorOrNo;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(DesignatorList!=null) DesignatorList.accept(visitor);
        if(DesignatorOrNo!=null) DesignatorOrNo.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorList!=null) DesignatorList.traverseTopDown(visitor);
        if(DesignatorOrNo!=null) DesignatorOrNo.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorList!=null) DesignatorList.traverseBottomUp(visitor);
        if(DesignatorOrNo!=null) DesignatorOrNo.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("DesignList(\n");

        if(DesignatorList!=null)
            buffer.append(DesignatorList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(DesignatorOrNo!=null)
            buffer.append(DesignatorOrNo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [DesignList]");
        return buffer.toString();
    }
}
