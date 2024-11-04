// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class OneIdent extends IdentList {

    private Ident Ident;

    public OneIdent (Ident Ident) {
        this.Ident=Ident;
        if(Ident!=null) Ident.setParent(this);
    }

    public Ident getIdent() {
        return Ident;
    }

    public void setIdent(Ident Ident) {
        this.Ident=Ident;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Ident!=null) Ident.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Ident!=null) Ident.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Ident!=null) Ident.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("OneIdent(\n");

        if(Ident!=null)
            buffer.append(Ident.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [OneIdent]");
        return buffer.toString();
    }
}
