package com.bianisoft.engine.helper.datatypes;


public class Real{
    public float m_fValue;


    public Real()						{}
    public Real(float p_fValue)			{m_fValue= p_fValue;}
    public float set(float p_fValue)	{return (m_fValue= p_fValue);}
    public float get()					{return m_fValue;}
    public float add(float p_fValue)	{return (m_fValue+= p_fValue);}
    public String toString()			{return "Real: " + m_fValue;}
}
