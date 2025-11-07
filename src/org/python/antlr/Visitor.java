package org.python.antlr;

import org.python.antlr.ast.VisitorBase;

/**
 * Visitor
 */
public class Visitor extends VisitorBase {

    /**
     * Visit each of the children one by one.
     * @param node The node whose children will be visited.
     */
    @Override
    public void traverse(PythonTree node) throws Exception {
        node.traverse(this);
    }

    /**
     * Visit
     * 
     * @param nodes
     *            nodes
     * @throws Exception
     *             exception
     */
    public void visit(PythonTree[] nodes) throws Exception {
        for (int i = 0; i < nodes.length; i++) {
            visit(nodes[i]);
        }
    }

    /**
     * Visit the node by calling a visitXXX method.
     * 
     * @param node
     *            node
     * @return o
     * @throws Exception
     *             exception
     */
    public Object visit(PythonTree node) throws Exception {
        @SuppressWarnings("unchecked")
        Object ret = node.accept(this);
        return ret;
    }

    @Override
    protected Object unhandled_node(PythonTree node) throws Exception {
        return this;
    }

}
