/* ===============================================================================
*
* Part of the InfoGlue Content Management Platform (www.infoglue.org)
*
* ===============================================================================
*
*  Copyright (C)
* 
* This program is free software; you can redistribute it and/or modify it under
* the terms of the GNU General Public License version 2, as published by the
* Free Software Foundation. See the file LICENSE.html for more information.
* 
* This program is distributed in the hope that it will be useful, but WITHOUT
* ANY WARRANTY, including the implied warranty of MERCHANTABILITY or FITNESS
* FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
* 
* You should have received a copy of the GNU General Public License along with
* this program; if not, write to the Free Software Foundation, Inc. / 59 Temple
* Place, Suite 330 / Boston, MA 02111-1307 / USA.
*
* ===============================================================================
*/

package org.infoglue.calendar.actions;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.opensymphony.xwork.ActionSupport;

/**
* @author Mattias Bogeblad
*
* This is an abstract action used for all calendar actions.
*  Just to not have to put to much in the WebworkAbstractAction.
*/

public abstract class CalendarUploadAbstractAction extends CalendarAbstractAction
{
    protected File file;
    protected String assetKey;
    protected String fileContentType;
    protected String fileFileName;
    protected File[] files;
    protected String[] filesContentType;
    protected String[] filesFileName;

    public void setFile(File file) 
    {
        this.file = file;
    }

    public void setFileContentType(String fileContentType) 
    {
        this.fileContentType = fileContentType;
    }

    public void setFileFileName(String fileFileName) 
    {
        this.fileFileName = fileFileName;
    }

    public void setFiles(File[] files) 
    {
        this.files = files;
    }

    public void setFilesContentType(String[] filesContentType) 
    {
        this.filesContentType = filesContentType;
    }

    public void setFilesFileName(String[] filesFileName) 
    {
        this.filesFileName = filesFileName;
    }
    
    public File getFile()
    {
        return file;
    }
    
    public String getFileContentType()
    {
        return fileContentType;
    }
    
    public String getFileFileName()
    {
        return fileFileName;
    }
    
    public File[] getFiles()
    {
        return files;
    }
    
    public String[] getFilesContentType()
    {
        return filesContentType;
    }
    
    public String[] getFilesFileName()
    {
        return filesFileName;
    }
    
    public String getAssetKey()
    {
        return assetKey;
    }
    
    public void setAssetKey(String assetKey)
    {
        this.assetKey = assetKey;
    }
}

