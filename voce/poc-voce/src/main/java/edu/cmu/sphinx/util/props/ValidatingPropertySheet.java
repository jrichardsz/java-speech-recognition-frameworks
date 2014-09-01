/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) radix(10) lradix(10) 
// Source File Name:   ValidatingPropertySheet.java

package edu.cmu.sphinx.util.props;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package edu.cmu.sphinx.util.props:
//            PropertyException, PropertySheet, RawPropertyData, Registry, 
//            PropertyType, ConfigurationManager, Configurable

class ValidatingPropertySheet
    implements PropertySheet
{

    ValidatingPropertySheet(ConfigurationManager cm, Registry registry, RawPropertyData rpd)
        throws PropertyException
    {
        properties = new HashMap();
        this.cm = cm;
        this.registry = registry;
        Map raw = rpd.getProperties();
        className = rpd.getClassName();
        String key;
        Object val;
        for(Iterator i = raw.keySet().iterator(); i.hasNext(); setRaw(key, val))
        {
            key = (String)i.next();
            val = raw.get(key);
        }

    }

    void addProperty(String name, String value)
    {
        properties.put(name, value);
    }

    public void setString(String name, String value)
    {
        throw new UnsupportedOperationException();
    }

    public void setInt(String name, int value)
    {
        throw new UnsupportedOperationException();
    }

    public void setFloat(String name, float value)
    {
        throw new UnsupportedOperationException();
    }

    public void setRaw(String key, Object val)
        throws PropertyException
    {
        PropertyType type = registry.lookup(key);
        if(type == null)
            throw new PropertyException(registry.getOwner(), key, "Attempt to set unregistered property");
        if(val instanceof String)
        {
            String sval = (String)val;
            if(!isGlobalVariable(sval) && !type.isValid(val))
                throw new PropertyException(registry.getOwner(), key, "value (" + sval + ")" + " is not a valid " + type);
            properties.put(key, val);
        } else
        {
            if(!type.isValid(val))
                throw new PropertyException(registry.getOwner(), key, val + " is not a valid " + type);
            properties.put(key, val);
        }
    }

    public Object getRaw(String name)
        throws PropertyException
    {
        Object value = getRawNoReplacment(name);
        if(value == null)
            return null;
        if(value instanceof String)
        {
            String sval = (String)value;
            if(sval.startsWith("${"))
            {
                value = cm.globalLookup(sval);
                if(value == null)
                    throw new PropertyException(registry.getOwner(), name, "Can't find global property " + sval);
            }
        } else
        if(value instanceof List)
        {
            List lval = (List)value;
            ListIterator i = lval.listIterator();
            do
            {
                if(!i.hasNext())
                    break;
                String sval = (String)i.next();
                if(sval.startsWith("${"))
                {
                    String itemVal = cm.globalLookup(sval);
                    if(itemVal == null)
                        throw new PropertyException(registry.getOwner(), name, "Can't find global property " + sval);
                    i.set(itemVal);
                }
            } while(true);
        }
        return value;
    }

    public Object getRawNoReplacment(String name)
    {
        return properties.get(name);
    }

    public String getString(String name, String defaultValue)
        throws PropertyException
    {
        checkType(name, PropertyType.STRING);
        String value = (String)getRaw(name);
        if(value == null)
            value = defaultValue;
        return value;
    }

    public int getInt(String name, int defaultValue)
        throws PropertyException
    {
        checkType(name, PropertyType.INT);
        String val;
        try
        {
            val = (String)getRaw(name);
            if(val == null)
                return defaultValue;
        }
        catch(NumberFormatException e)
        {
            throw new PropertyException(registry.getOwner(), name, "bad integer format");
        }
        return Integer.parseInt(val);
    }

    public float getFloat(String name, float defaultValue)
        throws PropertyException
    {
        checkType(name, PropertyType.FLOAT);
        String val;
        try
        {
            val = (String)getRaw(name);
            if(val == null)
                return defaultValue;
        }
        catch(NumberFormatException e)
        {
            throw new PropertyException(registry.getOwner(), name, "bad float format");
        }
        return Float.parseFloat(val);
    }

    public double getDouble(String name, double defaultValue)
        throws PropertyException
    {
        checkType(name, PropertyType.DOUBLE);
        String val;
        try
        {
            val = (String)getRaw(name);
            if(val == null)
                return defaultValue;
        }
        catch(NumberFormatException e)
        {
            throw new PropertyException(registry.getOwner(), name, "bad double format");
        }
        return Double.parseDouble(val);
    }

    public boolean getBoolean(String name, boolean defaultValue)
        throws PropertyException
    {
        checkType(name, PropertyType.BOOLEAN);
        String val = (String)getRaw(name);
        if(val == null)
            return defaultValue;
        else
            return Boolean.valueOf((String)getRaw(name)).booleanValue();
    }

    public URL getResource(String name)
        throws PropertyException
    {
        URL url = null;
        checkType(name, PropertyType.RESOURCE);
        String location = (String)getRaw(name);
        if(location == null)
            throw new PropertyException(registry.getOwner(), name, "Required resource property '" + name + "' not set");
        Matcher jarMatcher = jarPattern.matcher(location);
        if(jarMatcher.matches())
        {
            String className = jarMatcher.group(1);
            String resourceName = jarMatcher.group(2);
            try
            {
                Class cls = Class.forName(className);
                url = cls.getResource(resourceName);
                if(url == null)
                {
                    String classPath = className.replaceAll("\\.", "/") + ".class";
                    url = cls.getClassLoader().getResource(classPath);
                    if(url != null)
                    {
                        String urlString = url.toString();
                        urlString = urlString.replaceAll("/" + classPath, resourceName);
                        try
                        {
                            url = new URL(urlString);
                        }
                        catch(MalformedURLException mfe)                       
                        {
                        	
                        	try{
            					url = new File(location).toURI().toURL();
            				}
            				catch(MalformedURLException e1){
            					throw new PropertyException(registry.getOwner(), name, "Bad FILE " + location + e1.getMessage());
            				}
                        	
                            throw new PropertyException(registry.getOwner(), name, "Bad URL " + urlString + mfe.getMessage());
                        }
                    }
                }
                if(url == null)
                    throw new PropertyException(registry.getOwner(), name, "Can't locate resource " + resourceName);
            }
            catch(ClassNotFoundException cnfe)
            {
                throw new PropertyException(registry.getOwner(), name, "Can't locate resource:/" + className);
            }
        } 
        else
        {
            if(location.indexOf(":") == -1)
                location = "file:" + location;
            try
            {
                url = new URL(location);
            }
            catch(MalformedURLException e)
            {
            	
            	try{
					url = new File(location).toURI().toURL();
				}
				catch(MalformedURLException e1){
					throw new PropertyException(registry.getOwner(), name, "Bad FILE or URL " + location + e.getMessage());
				}           
            }
        }
        return url;
    }

    public Configurable getComponent(String name, Class type)
        throws PropertyException
    {
        checkType(name, PropertyType.COMPONENT);
        String val = (String)getRaw(name);
        if(val == null)
            throw new PropertyException(registry.getOwner(), name, "Required component property '" + name + "' not set");
        Configurable c = null;
        try
        {
            c = cm.lookup(val);
            if(c == null)
                throw new PropertyException(registry.getOwner(), name, "Can't find component: " + val);
            if(!type.isInstance(c))
                throw new PropertyException(registry.getOwner(), name, "type mismatch. Expected type: " + type.getName() + " found component of type: " + c.getClass().getName());
        }
        catch(InstantiationException e)
        {
            throw new PropertyException(registry.getOwner(), name, "Can't instantiate: " + val + " " + e.getMessage());
        }
        return c;
    }

    public List getStrings(String name)
        throws PropertyException
    {
        checkType(name, PropertyType.STRING_LIST);
        Object obj = getRaw(name);
        if(obj == null)
            return EMPTY;
        if(obj instanceof List)
            return (List)obj;
        else
            throw new PropertyException(registry.getOwner(), name, "internal error");
    }

    public List getComponentList(String name, Class type)
        throws PropertyException
    {
        checkType(name, PropertyType.COMPONENT_LIST);
        List list = (List)getRaw(name);
        if(list == null)
            return EMPTY;
        List objectList = new ArrayList();
        for(Iterator i = list.iterator(); i.hasNext();)
        {
            String compName = (String)i.next();
            Configurable c = null;
            try
            {
                c = cm.lookup(compName);
                if(c == null)
                    throw new PropertyException(registry.getOwner(), name, "Can't find component: " + compName);
                if(!type.isInstance(c))
                    throw new PropertyException(registry.getOwner(), name, "type mismatch. Expected type: " + type.getName() + " found component of type: " + c.getClass().getName());
                objectList.add(c);
            }
            catch(InstantiationException e)
            {
                throw new PropertyException(registry.getOwner(), name, "Can't instantiate: " + compName);
            }
        }

        return objectList;
    }

    public String[] getNames()
    {
        Set keys = properties.keySet();
        return (String[])keys.toArray(new String[keys.size()]);
    }

    public ConfigurationManager getPropertyManager()
    {
        return cm;
    }

    private void checkType(String name, PropertyType expectedType)
        throws PropertyException
    {
        PropertyType registeredType = registry.lookup(name);
        if(registeredType == null)
            throw new PropertyException(registry.getOwner(), name, "Unknown property");
        if(registeredType != expectedType)
            throw new PropertyException(registry.getOwner(), name, "Type mismatch, requested " + expectedType + " registered type:" + registeredType);
        else
            return;
    }

    public String toString()
    {
        StringBuffer sb = new StringBuffer();
        String names[] = getNames();
        for(int j = 0; j < names.length; j++)
        {
            Object obj;
            try
            {
                obj = getRaw(names[j]);
            }
            catch(PropertyException e)
            {
                obj = "ERROR(not set)";
            }
            if(obj instanceof String)
            {
                String value = (String)obj;
                sb.append("<property name=\"");
                sb.append(names[j]);
                sb.append("\" value=\"");
                sb.append(value);
                sb.append("\"/>\n");
                continue;
            }
            if(!(obj instanceof List))
                continue;
            List values = (List)obj;
            sb.append("<list name=\"");
            sb.append(names[j]);
            sb.append("\">\n");
            for(int k = 0; k < values.size(); k++)
            {
                sb.append("    <item>");
                sb.append((String)values.get(k));
                sb.append("</item>\n");
            }

            sb.append("</list>\n");
        }

        return sb.toString();
    }

    public void dump(PrintStream out)
    {
        String names[] = getNames();
        for(int j = 0; j < names.length; j++)
        {
            Object obj;
            try
            {
                obj = getRaw(names[j]);
            }
            catch(PropertyException e)
            {
                obj = "ERROR(not set)";
            }
            if(obj instanceof String)
            {
                out.println("  " + names[j] + ": " + obj);
                continue;
            }
            if(!(obj instanceof List))
                continue;
            List values = (List)obj;
            out.print("  " + names[j] + ": ");
            for(int k = 0; k < values.size(); k++)
                out.println("        " + (String)values.get(k));

            out.println();
        }

    }

    private boolean isGlobalVariable(String val)
    {
        return val.startsWith("${");
    }

    private Level getLogLevel()
        throws PropertyException
    {
        Level level = null;
        String levelName = getString("logLevel", cm.getGlobalLogLevel());
        if(levelName == null)
            level = Level.WARNING;
        else
            try
            {
                level = Level.parse(levelName);
            }
            catch(IllegalArgumentException e)
            {
                throw new PropertyException(registry.getOwner(), "logLevel", "Bad 'level' specifier " + levelName);
            }
        return level;
    }

    public Logger getLogger()
        throws PropertyException
    {
        Logger logger = Logger.getLogger(registry.getOwner().getName());
        Level level = getLogLevel();
        logger.setLevel(level);
        return logger;
    }

    private ConfigurationManager cm;
    private Map properties;
    private Registry registry;
    private String className;
    private static final List EMPTY = new ArrayList();
    private static Pattern jarPattern = Pattern.compile("resource:/([.\\w]+?)!(.*)", 2);

}