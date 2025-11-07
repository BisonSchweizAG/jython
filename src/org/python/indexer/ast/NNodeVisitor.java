/**
 * Copyright 2009, Google Inc.  All rights reserved.
 * Licensed to PSF under a Contributor Agreement.
 */
package org.python.indexer.ast;

/**
 * Preorder-traversal node visitor interface.
 */
public interface NNodeVisitor {
    /**
     * Convenience exception for subclasses.  The caller that initiates
     * the visit should catch this exception if the subclass is expected
     * to throw it.
     */
    public static final class StopIterationException extends RuntimeException {
        public StopIterationException() {}
    }

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NAlias m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NAssert m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NAssign m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NAttribute m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NAugAssign m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NBinOp m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NBlock m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NBoolOp m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NBreak m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NCall m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NClassDef m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NCompare m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NComprehension m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NContinue m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NDelete m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NDict m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NEllipsis m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NExceptHandler m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NExec m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NFor m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NFunctionDef m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NGeneratorExp m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NGlobal m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NIf m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NIfExp m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NImport m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NImportFrom m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NIndex m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NKeyword m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NLambda m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NList m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NListComp m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NModule m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NName m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NNum m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NPass m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NPlaceHolder m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NPrint m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NQname m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NRaise m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NRepr m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NReturn m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NExprStmt m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NSlice m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NStr m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NSubscript m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NTryExcept m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NTryFinally m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NTuple m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NUnaryOp m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NUrl m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NWhile m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NWith m);

    /**
     * Visit
     * 
     * @param m
     *            m
     * @return visit
     */
    public boolean visit(NYield m);
}
