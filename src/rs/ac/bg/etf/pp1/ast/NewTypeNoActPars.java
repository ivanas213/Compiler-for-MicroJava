// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class NewTypeNoActPars extends NewType {

    public NewTypeNoActPars () {
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("NewTypeNoActPars(\n");

        buffer.append(tab);
        buffer.append(") [NewTypeNoActPars]");
        return buffer.toString();
    }
}
