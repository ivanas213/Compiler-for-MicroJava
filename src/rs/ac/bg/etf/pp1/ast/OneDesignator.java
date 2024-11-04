// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class OneDesignator extends DesignatorList {

    private DesignatorOrNo DesignatorOrNo;

    public OneDesignator (DesignatorOrNo DesignatorOrNo) {
        this.DesignatorOrNo=DesignatorOrNo;
        if(DesignatorOrNo!=null) DesignatorOrNo.setParent(this);
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
        if(DesignatorOrNo!=null) DesignatorOrNo.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(DesignatorOrNo!=null) DesignatorOrNo.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(DesignatorOrNo!=null) DesignatorOrNo.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneDesignator(\n");

        if(DesignatorOrNo!=null)
            buffer.append(DesignatorOrNo.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneDesignator]");
        return buffer.toString();
    }
}
