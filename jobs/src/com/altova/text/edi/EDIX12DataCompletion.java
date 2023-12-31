////////////////////////////////////////////////////////////////////////
//
// EDIX12DataCompletion.java
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

package com.altova.text.edi;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import com.altova.text.*;

public class EDIX12DataCompletion extends DataCompletion {
private final static Logger log =  LogManager.getLogger();
	private EDIX12Settings m_Settings = null;

	private ITextNode m_GroupRoot = null;

	private int mHLSegmentCounter = 0;
	private boolean mCompleteSingleConditions = false;
	private boolean mCompleteSingleValues = false;

	public EDIX12DataCompletion(TextDocument document, EDIX12Settings settings, String structurename) {
		super(document, structurename);
		m_Settings = settings;
	}

    public void completeData(ITextNode dataRoot, Particle rootParticle) {
		completeMandatory(dataRoot, rootParticle);
        completeEnvelope(dataRoot);
	}

    private void completeEnvelope(ITextNode envelope)
    {
        if (!envelope.getName().equals("Envelope"))
			throw new com.altova.AltovaException("completeEnvelope: root node is not an envelope");

        makeSureExists(envelope, "Interchange");

        TextNodeList interchanges = envelope.getChildren().filterByName("Interchange");
        for (int i=0; i< interchanges.size(); ++i)
            completeInterchange(interchanges.getAt(i));
    }

	private void completeInterchange(ITextNode interchange) {
		makeSureExists(interchange, "ISA");
		m_GroupRoot = makeSureExists(interchange, "Group");
		makeSureExists(interchange, "IEA");

		ITextNode isa = getKid(interchange, "ISA");
		ITextNode iea = getKid(interchange, "IEA");

		ITextNode FI01 = makeSureExists(isa, "FI01");
		conservativeSetValue(FI01, "00");
		ITextNode FI02 = makeSureExists(isa, "FI02");
		conservativeSetValue(FI02, "          ");
		ITextNode FI03 = makeSureExists(isa, "FI03");
		conservativeSetValue(FI03, "00");
		ITextNode FI04 = makeSureExists(isa, "FI04");
		conservativeSetValue(FI04, "          ");
		ITextNode FI05_1 = makeSureExists(isa, "FI05_1");
		conservativeSetValue(FI05_1, "ZZ");
		ITextNode FI05_2 = makeSureExists(isa, "FI05_2");
		conservativeSetValue(FI05_2, "ZZ");
		ITextNode FI08 = makeSureExists(isa, "FI08");
		conservativeSetValue(FI08, getCurrentDateAsEDIString(2));
		ITextNode FI09 = makeSureExists(isa, "FI09");
		conservativeSetValue(FI09, getCurrentTimeAsEDIString());
		
		if( isOldISAVersion() ) {
			makeSureExists(isa, "FI10");
		}
		else {
			ITextNode FI65 = makeSureExists(isa, "FI65");
			conservativeSetValue(FI65, m_Settings.getServiceChars().getRepetitionSeparator());
		}

		ITextNode FI11 = makeSureExists(isa, "FI11");
		conservativeSetValue(FI11, m_Settings
				.getInterchangeControlVersionNumber());
		ITextNode FI12 = makeSureExists(isa, "FI12");
		conservativeSetValue(FI12, "000000000");
		ITextNode FI13 = makeSureExists(isa, "FI13");
		conservativeSetValue(FI13, m_Settings.getRequestAcknowledgement() ? "1"
				: "0");
		ITextNode FI14 = makeSureExists(isa, "FI14");
		conservativeSetValue(FI14, "P");
		ITextNode FI15 = makeSureExists(isa, "FI15");
		conservativeSetValue(FI15, m_Settings.getServiceChars()
				.getComponentSeparator());

		ITextNodeList groups = interchange.getChildren().filterByName("Group");
		for (int j = 0; j < groups.size(); ++j) {
			m_GroupRoot = groups.getAt(j);
			makeSureExists(m_GroupRoot, "GS");
			makeSureExists(m_GroupRoot, "GE");
			completeGroup();
		}

		ITextNode IEAFI16 = makeSureExists(iea, "FI16");
		conservativeSetValue(IEAFI16, interchange.getChildren()
				.filterByName("Group").size());
		ITextNode IEAFI12 = makeSureExists(iea, "FI12");
		conservativeSetValue(IEAFI12, FI12.getValue().trim());

	}

	private void completeGroup() {
		ITextNodeList multiMessages = null;
		for( String sMessageType : m_Document.getMessageTypes()) {
			multiMessages  = m_GroupRoot.getChildren().filterByName("Message_" + sMessageType);
			for(int i=0; i< multiMessages.size(); ++i) {
				makeSureExists(multiMessages.getAt(i), "ST");
				makeSureExists(multiMessages.getAt(i), "SE");
				completeMandatory(multiMessages.getAt(i), m_Document.getMessage(sMessageType).getRootParticle());
				completeMessage(m_Document.getMessage(sMessageType), multiMessages.getAt(i));
			}
		}
		
		ITextNodeList messages = m_GroupRoot.getChildren().filterByName("Message");
		for (int i = 0; i < messages.size(); ++i) {
			makeSureExists( messages.getAt(i), "ST");
			makeSureExists( messages.getAt(i), "SE");
			completeMessage(m_Document.getMessage(m_Settings.getMessageType()), messages.getAt(i));
		}
		
		int msgCount = messages.size() + ( multiMessages != null ? multiMessages.size() : 0 );
		
		ITextNode GS = m_GroupRoot.getChildren().filterByName("GS").getAt(0);
		if (GS != null) {
			ITextNode GE = m_GroupRoot.getChildren().filterByName("GE")
					.getAt(0);
			ITextNode GSF373 = makeSureExists(GS, "F373");
			conservativeSetValue(GSF373, getCurrentDateAsEDIString(4));
			ITextNode GSF337 = makeSureExists(GS, "F337");
			conservativeSetValue(GSF337, getCurrentTimeAsEDIString());
			ITextNode GSF28 = GS.getChildren().filterByName("F28").getAt(0);
			ITextNode GEF97 = makeSureExists(GE, "F97");
			conservativeSetValue(GEF97, msgCount);
					
			if (GSF28 != null)
			{
				ITextNode GEF28 = makeSureExists(GE, "F28");
				conservativeSetValue(GEF28, GSF28.getValue());
			}
		}
	}

	private void completeMessage(Message message, ITextNode messageNode) {
		String sMessageType = message.getMessageType();

		ITextNode ST = makeSureExists(messageNode, "ST");
		ITextNode SE = makeSureExists(messageNode, "SE");
		ITextNode STF143 = makeSureExists(ST, "F143");
		conservativeSetValue(STF143, sMessageType.substring(0, 3));
		ITextNode SEF96 = makeSureExists(SE, "F96");
		long segmentcount = getSegmentChildrenCount(ST.getParent());
		conservativeSetValue(SEF96, segmentcount);
		ITextNode STF329 = makeSureExists(ST, "F329");
		ITextNode SEF329 = makeSureExists(SE, "F329");
		conservativeSetValue(SEF329, STF329.getValue());

		mHLSegmentCounter = 0;
		if (message.shouldCompleteHLSegments())
			completeHLSegments(messageNode, 0, message.getRootParticle());

		if (message.shouldCompleteSingleConditions() || message.shouldCompleteSingleValues())
		{
			mCompleteSingleConditions = message.shouldCompleteSingleConditions();
			mCompleteSingleValues = message.shouldCompleteSingleValues();
			completeConditionsAndValues(messageNode, message.getRootParticle());
		}
	}

	private boolean completeHLSegments(ITextNode group, int parent, Particle particle)
	{
		boolean hasChildren = false;

		ITextNode hl = group.getChildren().getFirstNodeByName("HL");
		Particle hl_particle = getParticleByPath(particle, "HL");
		
		if (hl != null)
		{
			// set parent and ID
			ITextNode hlID = makeSureExists(hl, "F628");
			hlID.setValue( String.valueOf(++mHLSegmentCounter) );
			if (parent != 0)
			{
				ITextNode hlParentID = makeSureExists(hl, "F734");
				hlParentID.setValue( String.valueOf(parent) );
			}
		}

		parent = mHLSegmentCounter;

		ITextNodeList children = group.getChildren();
		for( int i = 0 ; i < children.size() ; ++i )
		{
			ITextNode node = children.getAt(i);
			if (node.getNodeClass() == ITextNode.Group)
			{
				Particle p = getParticleByPath(particle, node.getName());
				hasChildren = completeHLSegments(node, parent, p);
			}
		}

		if (hl != null)
		{
			Particle p_F736 = getParticleByPath(hl_particle, "F736");
			
			if ( p_F736.getMinOccurs() > 0 )
			{	
				// Set has children flag
				ITextNode hlChildCode = makeSureExists(hl, "F736");
				hlChildCode.setValue( hasChildren ? "1" : "0" );
			}
			
			return true;
		}

		return hasChildren;
	}

	private void completeConditionsAndValues(ITextNode node, Particle particle)
	{
		if (particle == null)
			throw new Error ("Invalid parameter");

		node.setNodeClass( particle.getNode().getNodeClass() );

		if (node.getNodeClass() == ITextNode.Segment)
		{
			if (mCompleteSingleValues && particle.getNode().getConditionPath().length() > 0)
			{
				ITextNode n = getChildNodeByPath(node, particle.getNode().getConditionPath());
				if (n == null)
					n = createTree(node, particle.getNode().getConditionPath());

				if (particle.getNode().getConditionValue().length() > 0)
					conservativeSetValue(n, particle.getNode().getConditionValue());
				else
				{
					Particle p = getParticleByPath(particle, particle.getNode().getConditionPath());
					if (p != null && p.getCodeValues().length == 1)
						conservativeSetValue(n, p.getCodeValues()[0]);
				}
			}
		}

		if (node.getNodeClass() == ITextNode.Segment || node.getNodeClass() == ITextNode.Composite)
		{
			for(Particle p : particle.getNode().getChildren())
			{
				if (p.getMinOccurs() > 0)
				{
					if (p.getNode().getNodeClass() == ITextNode.DataElement && p.getCodeValues().length != 1)
						continue;

					ITextNode n = makeSureExists(node, p.getName());
					completeConditionsAndValues(n, p);
				}
			}
		}
		else if (node.getNodeClass() == ITextNode.DataElement)
		{
			if (mCompleteSingleValues && particle.getCodeValues().length == 1)
				conservativeSetValue(node, particle.getCodeValues()[0]);
		}

		ITextNodeList children = node.getChildren();
		for( int i = 0 ; i < children.size() ; ++i )
		{
			ITextNode n = children.getAt(i);
			completeConditionsAndValues(n, getParticleByPath(particle, n.getName()));
		}
	}

	private Particle getParticleByPath(Particle p, String path)
	{
		for(Particle childParticle : p.getNode().getChildren())
		{
			String sName = childParticle.getName();
			int i = sName.indexOf('/');
			if (i != -1)
			{
				if (childParticle.getName().equals( path.substring(0, i) ))
					return getParticleByPath(childParticle, path.substring(i + 1));
			}

			if (childParticle.getName().equals( path ))
				return childParticle;
		}

		return null;
	}

	private ITextNode getChildNodeByPath(ITextNode node, String path)
	{
		ITextNodeList children = node.getChildren();
		for( int k = 0 ; k < children.size() ; ++k )
		{
			ITextNode n = children.getAt(k);

			int i = path.indexOf('/');
			if (i != -1)
			{
				if (n.getName().equals( path.substring(0, i) ))
					return getChildNodeByPath(n, path.substring(i + 1));
			}

			if (n.getName().equals( path ))
				return n;
		}

		return null;
	}

	private ITextNode createTree(ITextNode node, String path)
	{
		int i = path.indexOf('/');
		if (i != -1)
		{
			ITextNode n = makeSureExists(node, path.substring(0, i));
			return createTree(n, path.substring(i + 1));
		}

		return makeSureExists(node, path);
	}

	boolean isOldISAVersion()
	{
		return m_Settings.getRelease().equals("3040") ||
			m_Settings.getRelease().equals("3050") ||
			m_Settings.getRelease().equals("3060") ||
			m_Settings.getRelease().equals("3070") ||
			m_Settings.getRelease().equals("4010");
	}
}
