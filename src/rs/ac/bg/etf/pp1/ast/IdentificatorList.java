// generated with ast extension for cup
// version 0.8
// 14/8/2023 14:9:47


package rs.ac.bg.etf.pp1.ast;

public class IdentificatorList extends IdentList {

    private IdentList IdentList;
    private Ident Ident;

    public IdentificatorList (IdentList IdentList, Ident Ident) {
        this.IdentList=IdentList;
        if(IdentList!=null) IdentList.setParent(this);
        this.Ident=Ident;
        if(Ident!=null) Ident.setParent(this);
    }

    public IdentList getIdentList() {
        return IdentList;
    }

    public void setIdentList(IdentList IdentList) {
        this.IdentList=IdentList;
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
        if(IdentList!=null) IdentList.accept(visitor);
        if(Ident!=null) Ident.accept(visitor);
    }

    public void traverseTopDown(Visitor visitor) {
        accept(visitor);
        if(IdentList!=null) IdentList.traverseTopDown(visitor);
        if(Ident!=null) Ident.traverseTopDown(visitor);
    }

    public void traverseBottomUp(Visitor visitor) {
        if(IdentList!=null) IdentList.traverseBottomUp(visitor);
        if(Ident!=null) Ident.traverseBottomUp(visitor);
        accept(visitor);
    }

    public String toString(String tab) {
        StringBuffer buffer=new StringBuffer();
        buffer.append(tab);
        buffer.append("IdentificatorList(\n");

        if(IdentList!=null)
            buffer.append(IdentList.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        if(Ident!=null)
            buffer.append(Ident.toString("  "+tab));
        else
            buffer.append(tab+"  null");
        buffer.append("\n");

        buffer.append(tab);
        buffer.append(") [IdentificatorList]");
        return buffer.toString();
    }
}
