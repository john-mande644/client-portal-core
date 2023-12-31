////////////////////////////////////////////////////////////////////////
//
// TextNodeList.java
//
// This file was generated by MapForce 2015r3.
//
// YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE
// OVERWRITTEN WHEN YOU RE-RUN CODE GENERATION.
//
// Refer to the MapForce Documentation for further details.
// http://www.altova.com/mapforce
//
////////////////////////////////////////////////////////////////////////

package com.altova.text;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TextNodeList implements ITextNodeList {
private final static Logger log =  LogManager.getLogger();
	private List<ITextNode> m_List = null;
	private HashMap<String, List<ITextNode>> m_NamesToNodes = null;

	private ITextNode m_Owner = null;

	void buildNamedNodes() {
		if (m_NamesToNodes != null)
			return;
			
		m_NamesToNodes = new HashMap<String, List<ITextNode>>();

		for (int i=0; i< m_List.size(); i++)
			addToTable( (ITextNode) m_List.get(i));
	}
	
	private void addToTable(ITextNode rhs) {
		
		String name = rhs.getName();
		
		if (!m_NamesToNodes.containsKey(name))
			m_NamesToNodes.put(name, new ArrayList<ITextNode>());
		List<ITextNode> list = m_NamesToNodes.get(name);
		list.add(rhs);
	}

	private void removeFromTable(ITextNode rhs) {
		String name = rhs.getName();
			
		List<ITextNode> list = m_NamesToNodes.get(name);
		list.remove(rhs);
	}

	public TextNodeList(ITextNode owner) {
		m_Owner = owner;
	}

	public void add(ITextNode rhs) {
		if (null == rhs)
			return;
		
		if (m_List == null)
			m_List = new ArrayList<ITextNode>();
		if (m_NamesToNodes != null)
			addToTable(rhs);
		
		m_List.add(rhs);
		rhs.setParent(m_Owner);
	}

	public void insertAt(ITextNode rhs, int index) {
		 if (m_List == null) 
                m_List = new ArrayList<ITextNode>();
		if (m_NamesToNodes != null)
			addToTable(rhs);
		 
		m_List.add(index, rhs);
		rhs.setParent(m_Owner);
	}

	public void removeAt(int index) {
		if (m_List == null)
                return;
		
		if (m_NamesToNodes != null)
			removeFromTable((ITextNode) m_List.get(index));
		m_List.remove(index);
	}

	public int size() {
		return m_List == null ? 0: m_List.size();
	}

	public ITextNode getAt(int index) {
		if (m_List == null || (0 > index) || (index >= m_List.size()))
			return null;
		return (ITextNode) m_List.get(index);
	}

	public boolean contains(ITextNode rhs) {
		if (m_List == null)
                return false;
		
		for (int i = 0; i < m_List.size(); ++i) {
			ITextNode kid = this.getAt(i);
			if (kid == rhs)
				return true;
			if (kid.getChildren().contains(rhs))
				return true;
		}
		return false;
	}

	public TextNodeList filterByName(String name) {
		TextNodeList result = new TextNodeList(m_Owner);
		if (m_List == null)
			return result;

		buildNamedNodes();

		if (!m_NamesToNodes.containsKey(name))
			return result;
		List<ITextNode> list = m_NamesToNodes.get(name);
		result.m_List = new ArrayList<ITextNode>();
		result.m_List.addAll(list);
		return result;
	}
	
   	public void removeByName(String name) {
   		if (m_List == null)
   			return;
   		
		if (m_NamesToNodes != null)
			m_NamesToNodes.remove(name);
   		
		for (int i = 0; i < m_List.size();) {
			ITextNode kid = this.getAt(i);
			if (kid.getName().equals(name))
				m_List.remove(i);
			else
				++i;
		}
   	}
   	
	public ITextNode getFirstNodeByName(String name) {
		if (m_List == null)
                return null;
		
		 buildNamedNodes();
		
		if (!m_NamesToNodes.containsKey(name))
			return null;
		
		TextNodeList children = filterByName(name);
		return children.size() == 0 ? null : children.getAt(0);
	}
	
	public ITextNode getLastNodeByName(String name) {
		if (m_List == null)
                return null;
		
		 buildNamedNodes();
		
		if (!m_NamesToNodes.containsKey(name))
			return null;
		
		TextNodeList children = filterByName(name);
		return children.size() == 0 ? null : children.getAt(children.size()-1);
	}
	
	public void moveNode(ITextNode rhs, int index) {
		if (m_List == null)
                return;
		
		int nowIndex = m_List.indexOf(rhs);
		
		if (index != nowIndex)
		{
			m_List.remove(nowIndex);
			if (index > nowIndex)
				m_List.add(index-1, rhs);
			else
				m_List.add(index, rhs);
		}
	}
}
