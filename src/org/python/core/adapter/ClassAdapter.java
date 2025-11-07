package org.python.core.adapter;

/**
 * ClassAdapter
 */
public abstract class ClassAdapter implements PyObjectAdapter {

    /**
     * Constructor
     * 
     * @param adaptedClass
     *            adaptedClass
     */
	public ClassAdapter(Class adaptedClass) {
		this.adaptedClass = adaptedClass;
	}

    /**
     * GetAdaptedClass
     * 
     * @return class
     */
	public Class getAdaptedClass() {
		return adaptedClass;
	}

	public boolean canAdapt(Object o) {
		return adaptedClass.getClass().equals(adaptedClass);
	}

	private Class adaptedClass;

}
