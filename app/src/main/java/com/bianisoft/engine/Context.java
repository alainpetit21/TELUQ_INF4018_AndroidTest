/* This file is part of the Bianisoft game library.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *----------------------------------------------------------------------
 * Copyright (C) Alain Petit - alainpetit21@hotmail.com
 *
 * 18/12/10			0.1 First beta initial Version.
 * 12/09/11			0.1.2 Moved everything to a com.bianisoft
 *
 *-----------------------------------------------------------------------
 */
package com.bianisoft.engine;


//Standard Java imports
import java.util.ArrayList;

//Bianisoft imports
import com.bianisoft.engine._3d.Object3D;

import javax.microedition.khronos.opengles.GL10;


public class Context extends Obj{
    public static final int COUNTAINER_TYPE_INFINITE	= 0;
    public static final int COUNTAINER_TYPE_WORLD_2D	= 1;
    public static final int COUNTAINER_TYPE_WORLD_3D	= 2;
    public static final int COUNTAINER_TYPE_CAMERA_2D	= 3;
    public static final int COUNTAINER_TYPE_CAMERA_3D	= 4;

    private Obj			m_objWithKeyFocus;

    protected ArrayList<PhysObj>	m_vecPhysObj			= new ArrayList<PhysObj>();

    public int	m_nIndex;

    public boolean	m_is3DFirst= true;


    public Context(){
        super(IDCLASS_Context);
        m_objWithKeyFocus= this;
    }

    public void activate(){

    }

    public void deActivate(){

    }

    public void removeAllChilds(){
    }

    public void removeChild(Object3D p_obj){
    }

    public void removeChild(Drawable p_obj){
    }

    public void addChild(Object3D p_obj3D)	{addChild(p_obj3D, false, false);}
    public void addChild(Object3D p_obj3D, boolean p_isAttachedToCamera)	{addChild(p_obj3D, p_isAttachedToCamera, false);}
    public void addChild(Object3D p_obj3D, boolean p_isAttachedToCamera, boolean p_isInInfinity){
        m_vecPhysObj.add(p_obj3D);
    }

    public void addChild(Drawable p_objDrawable)	{addChild(p_objDrawable, false, false);}
    public void addChild(Drawable p_objDrawable, boolean p_isAttachedToCamera)	{addChild(p_objDrawable, p_isAttachedToCamera, false);}
    public void addChild(Drawable p_objDrawable, boolean p_isAttachedToCamera, boolean p_isInInfinity){
        m_vecPhysObj.add(p_objDrawable);
    }

    public void addChild(PhysObj p_physObj){
        m_vecPhysObj.add(p_physObj);
    }

    public void manage(float p_fRatioMovement){
        for(PhysObj physObj1 : m_vecPhysObj){
            physObj1.manage(p_fRatioMovement);
        }
    }

    public void draw(GL10 gl) {
        for(PhysObj physObject : m_vecPhysObj)
            ((Drawable)physObject).draw(gl);
    }

    public void loadAllRes(GL10 gl) {
        for(PhysObj physObject : m_vecPhysObj)
                ((Drawable)physObject).loadRes(gl);
    }

}
