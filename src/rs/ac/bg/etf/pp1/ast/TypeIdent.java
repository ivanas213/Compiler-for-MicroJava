// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class TypeIdent implements SyntaxNode {

    private SyntaxNode parent;
    private int line;
    private TypeFormPars TypeFormPars;
    private IdentFormPars IdentFormPars;
    private TypeIdentBracks TypeIdentBracks;

    public TypeIdent (TypeFormPars TypeFormPars, IdentFormPars IdentFormPars, TypeIdentBracks TypeIdentBracks) {
        this.TypeFormPars=TypeFormPars;
        if(TypeFormPars!=null) TypeFormPars.setParent(this);
        this.IdentFormPars=IdentFormPars;
        if(IdentFormPars!=null) IdentFormPars.setParent(this);
        this.TypeIdentBracks=TypeIdentBracks;
        if(TypeIdentBracks!=null) TypeIdentBracks.setParent(this);
    }

    public TypeFormPars getTypeFormPars() {
        return TypeFormPars;
    }

    public void setTypeFormPars(TypeFormPars TypeFormPars) {
        this.TypeFormPars=TypeFormPars;
    }

    public IdentFormPars getIdentFormPars() {
        return IdentFormPars;
    }

    public void setIdentFormPars(IdentFormPars IdentFormPars) {
        this.IdentFormPars=IdentFormPars;
    }

    public TypeIdentBracks getTypeIdentBracks() {
        return TypeIdentBracks;
    }

    public void setTypeIdentBracks(TypeIdentBracks TypeIdentBracks) {
        this.TypeIdentBracks=TypeIdentBracks;
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
        if(TypeFormPars!=null) TypeFormPars.accept(visitor);
        if(IdentFormPars!=null) IdentFormPars.accept(visitor);
        if(TypeIdentBracks!=null) TypeIdentBracks.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(TypeFormPars!=null) TypeFormPars.traverseTopDown(visitor);
        if(IdentFormPars!=null) IdentFormPars.traverseTopDown(visitor);
        if(TypeIdentBracks!=null) TypeIdentBracks.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(TypeFormPars!=null) TypeFormPars.traverseBottomUp(visitor);
        if(IdentFormPars!=null) IdentFormPars.traverseBottomUp(visitor);
        if(TypeIdentBracks!=null) TypeIdentBracks.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("TypeIdent(\n");

        if(TypeFormPars!=null)
            buffer.append(TypeFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(IdentFormPars!=null)
            buffer.append(IdentFormPars.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(TypeIdentBracks!=null)
            buffer.append(TypeIdentBracks.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [TypeIdent]");
        return buffer.toString();
    }
}
