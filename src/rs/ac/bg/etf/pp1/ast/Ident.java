// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class Ident implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    public rs.etf.pp1.symboltable.concepts.Obj obj = null;

    private String varIdent;
    private Brack Brack;

    public Ident (String varIdent, Brack Brack) {
        this.varIdent=varIdent;
        this.Brack=Brack;
        if(Brack!=null) Brack.setParent(this);
    }

    public String getVarIdent() {
        return varIdent;
    }

    public void setVarIdent(String varIdent) {
        this.varIdent=varIdent;
    }

    public Brack getBrack() {
        return Brack;
    }

    public void setBrack(Brack Brack) {
        this.Brack=Brack;
    }

    public SyntaxNode getParent() {
        return parent;
    }

    public void setParent(SyntaxNode parent) {
        this.parent=parent;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line=line;
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void childrenAccept(Visitor visitor) {
        if(Brack!=null) Brack.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(Brack!=null) Brack.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(Brack!=null) Brack.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("Ident(\n");

        buffer.append(" "+tab+varIdent);
        buffer.append("\n");

        if(Brack!=null)
            buffer.append(Brack.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [Ident]");
        return buffer.toString();
    }
}
