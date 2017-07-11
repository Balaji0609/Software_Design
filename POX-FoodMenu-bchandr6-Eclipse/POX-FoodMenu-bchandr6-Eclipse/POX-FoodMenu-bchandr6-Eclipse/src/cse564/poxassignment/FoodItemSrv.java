package cse564.poxassignment;

import java.io.IOException;
//import java.io.OutputStream;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.net.InetSocketAddress;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.codehaus.jackson.map.*;

//import java.io.BufferedReader;
import java.io.ByteArrayInputStream;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/FoodItem")
public class FoodItemSrv
{
	@SuppressWarnings("unchecked")
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces("application/xml")
    public String HandlePost(@FormParam("message")String Req)
    {
        	Object obj = new Object();
        	Iterator<?> it;
        	JSONArray jsonarray ;
        	JSONObject jsonobj;
            String resp = "";
            System.out.println(Req);
            int max_id = 0;

	         try
	         {
	            	
            	JSONParser 	parser = new JSONParser();
            	try
            	{
            		obj = parser.parse(new FileReader("JSON/FoodItemData.json"));
            	}
            	catch(FileNotFoundException e)
            	{
            		e.printStackTrace();
            	}
            	catch(IOException e)
            	{
            		e.printStackTrace();
            	}
            	catch(ParseException e)
            	{
            		e.printStackTrace();
            	}
            	jsonobj = (JSONObject)obj;
        		jsonarray = (JSONArray)jsonobj.get("FoodItemData");
        		it = jsonarray.iterator();

        		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document inputDoc = dBuilder
                        .parse(new InputSource(new ByteArrayInputStream(Req.getBytes("utf-8"))));
                inputDoc.getDocumentElement().normalize();
                if (Req.contains("SelectedFoodItems"))
                {
                    System.out.println("GetFoodRequest");
                    NodeList inpList = inputDoc.getElementsByTagName("FoodItemId");
                    resp = "<RetrievedFoodItems xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n";

                    for (int i = 0; i < inpList.getLength(); i++)
                    {
                        String itemId = ((Element) inpList.item(i)).getTextContent();
                        it = jsonarray.iterator();
                        boolean match = false;
                        while(it.hasNext())
                        {
                        		JSONObject TJsonobj = (JSONObject) it.next();
                                String curr_id = (String)TJsonobj.get("id");
                                if (curr_id.equals(itemId))
                                {
                                    match = true;
                                    resp += "<FoodItem country=\"" + TJsonobj.get("-country") + "\">\n";
                                    resp += "<id>" + (String)TJsonobj.get("id") + "</id>\n";
                                    resp += "<name>" + (String)TJsonobj.get("name")+ "</name>\n";
                                    resp += "<description>" + (String)TJsonobj.get("descrciption") + "</description>\n";
                                    resp += "<category>" + (String)TJsonobj.get("category") + "</category>\n";
                                    resp += "<price>" + (String)TJsonobj.get("price") + "</price>\n";
                                    resp += "</FoodItem>\n";
                                }
                        }

                        if (match == false)
                        {
                            resp += "<InvalidFoodItem>\n";
                            resp += "<FoodItemId>" + itemId + "</FoodItemId>\n";
                            resp += "</InvalidFoodItem>\n";
                        }
                    }
                    resp += "</RetrievedFoodItems>";
                }
                else if (Req.contains("NewFoodItems"))
                {
                    System.out.println("AddFoodRequest");
                    NodeList inpList = inputDoc.getElementsByTagName("FoodItem");
                    String itemName = ((Element) inpList.item(0)).getElementsByTagName("name").item(0).getTextContent();
                    String CatName = ((Element) inpList.item(0)).getElementsByTagName("category").item(0).getTextContent();
                    it = jsonarray.iterator();

                    while(it.hasNext())
                    {
                		JSONObject TJsonobj = (JSONObject) it.next();
                        int curr_id = Integer.parseInt((String)TJsonobj.get("id"));
                        String itemn = (String)TJsonobj.get("name");
                        String cat =(String)TJsonobj.get("category");
                        if (max_id < curr_id)
                            max_id = curr_id;

                        if (itemn.equals(itemName) && cat.equals(CatName))
                        {
                            resp = "<FoodItemExists xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n";
                            resp += "<FoodItemId>" + curr_id + "</FoodItemId>\n";
                            resp += "</FoodItemExists>";
                        }
                    
                    }
                }
                else
                {
                    System.out.println("Invalid XML message");
                    resp = "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
                }

                if (resp.length() == 0)
                {
                    resp = "<FoodItemAdded xmlns=\"http://cse564.asu.edu/PoxAssignment\">\n";
                    resp += "<FoodItemId>" + (++max_id) + "</FoodItemId>";
                    resp += "</FoodItemAdded>";
                    
                    try
                    {
                    NodeList inpList = inputDoc.getElementsByTagName("FoodItem");

                    String temp;
                    
                    JSONObject CopyObj = new JSONObject();
                    
                    temp = (String)((Element) inpList.item(0)).getAttribute("country");
                    
                    CopyObj.put("-country",temp);
                    String id = Integer.toString(max_id);
                    CopyObj.put("id", id);

                    temp =  ((Element) inpList.item(0)).getElementsByTagName("name").item(0).getTextContent();
                    CopyObj.put("name", temp);
                    
                    temp =  ((Element) inpList.item(0)).getElementsByTagName("description").item(0).getTextContent();
                    CopyObj.put("description", temp);
                    
                    temp =  ((Element) inpList.item(0)).getElementsByTagName("category").item(0).getTextContent();
                    CopyObj.put("category", temp);
                    
                    temp =  ((Element) inpList.item(0)).getElementsByTagName("price").item(0).getTextContent();
                    CopyObj.put("price", temp);
                    
                    
                    jsonarray.add(CopyObj);
                    FileWriter file = new FileWriter("JSON/FoodItemData.json");
                    ObjectMapper maper = new ObjectMapper();
                  
                    file.write(maper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonobj));
                    file.flush();
                    file.close();
                    }
                    catch(Exception e)
                    {
                    	e.printStackTrace();
                    }
                }
            }
            catch (SAXException e)
            {
                System.out.println("Parse Exception");
                resp = "<InvalidMessage xmlns=\"http://cse564.asu.edu/PoxAssignment\"/>";
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
	         
	         return resp;
        }

//    private static String getStringFromInputStream(InputStream is)
//    {
//        BufferedReader br = null;
//        StringBuilder sb = new StringBuilder();
//
//        String line;
//        try
//        {
//            br = new BufferedReader(new InputStreamReader(is));
//            while ((line = br.readLine()) != null)
//            {
//                sb.append(line);
//            }
//        }
//        catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//        finally
//        {
//            if (br != null)
//            {
//                try
//                {
//                    br.close();
//                }
//                catch (IOException e)
//                {
//                    e.printStackTrace();
//                }
//            }
//        }
//        return sb.toString();
//    }

}