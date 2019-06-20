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


public class Screen extends Obj{
    public static final int COUNTAINER_TYPE_INFINITE= 0;
    public static final int COUNTAINER_TYPE_WORLD	= 1;
    public static final int COUNTAINER_TYPE_CAMERA	= 3;

    protected ArrayList<PhysObj> arPhysObj = new ArrayList<>();

    public PhysObj objCam;
    public int	m_nIndex;


    public Screen(){
        super(IDCLASS_Context);
        objCam= new PhysObj();
        objCam.setPos(0,0,0);
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
        arPhysObj.add(p_obj3D);
    }

    public void addChild(Drawable p_objDrawable)	{addChild(p_objDrawable, false, false);}
    public void addChild(Drawable p_objDrawable, boolean p_isAttachedToCamera)	{addChild(p_objDrawable, p_isAttachedToCamera, false);}
    public void addChild(Drawable p_objDrawable, boolean p_isAttachedToCamera, boolean p_isInInfinity){
        arPhysObj.add(p_objDrawable);
    }

    public void addChild(PhysObj p_physObj){
        arPhysObj.add(p_physObj);
    }

    public PhysObj getChild(int p_nIdx){
        if(p_nIdx == -1){
            if(arPhysObj.size() > 0 )
                return arPhysObj.get(arPhysObj.size() -1);
            else
                return null;
        }

        return arPhysObj.get(p_nIdx);
    }

    private void manageSort(){
        for(int i= 0; i < arPhysObj.size(); ++i){
            PhysObj physObj1= arPhysObj.get(i);

            for(int j= i+1; j < arPhysObj.size(); ++j){
                PhysObj physObj2= arPhysObj.get(j);

                if(physObj2.getPosZ() > physObj1.getPosZ()){
                    arPhysObj.set(i, physObj2);
                    arPhysObj.set(j, physObj1);
                    i= -1;
                    break;
                }
            }
        }
    }

    public void manage(float p_fRatioMovement){
        manageSort();

        for(PhysObj physObj1 : arPhysObj){
            physObj1.manage(p_fRatioMovement);
        }
    }

    public void draw(GL10 gl) {
        for(PhysObj physObject : arPhysObj)
            ((Drawable)physObject).draw(gl);
    }

    public void loadAllRes(GL10 gl) {
        for(PhysObj physObject : arPhysObj)
                ((Drawable)physObject).loadRes(gl);
    }
}