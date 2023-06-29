
package com.owd.jobs.jobobjects.five9;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.owd.jobs.jobobjects.five9 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {
private final static Logger log =  LogManager.getLogger();

    private final static QName _GetCampaignDNISListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignDNISListResponse");
    private final static QName _ModifyUserCannedReports_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserCannedReports");
    private final static QName _SkillIsNotAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "SkillIsNotAssignedFault");
    private final static QName _UserSkillRemoveResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "userSkillRemoveResponse");
    private final static QName _CreateDispositionResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createDispositionResponse");
    private final static QName _ModifyCampaignProfileFilterOrderResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileFilterOrderResponse");
    private final static QName _AddDispositionsToCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addDispositionsToCampaign");
    private final static QName _ModifyCampaignLists_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignLists");
    private final static QName _GetReasonCodeByType_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReasonCodeByType");
    private final static QName _AddListsToCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addListsToCampaignResponse");
    private final static QName _CreateCallVariablesGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createCallVariablesGroup");
    private final static QName _AsyncAddRecordsToListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "asyncAddRecordsToListResponse");
    private final static QName _UserSkillModify_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "userSkillModify");
    private final static QName _GetCampaignProfileFilter_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignProfileFilter");
    private final static QName _DeleteContactField_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteContactField");
    private final static QName _ImportSizeLimitExceededFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ImportSizeLimitExceededFault");
    private final static QName _DeleteFromContactsFtpResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromContactsFtpResponse");
    private final static QName _GetSkillInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillInfo");
    private final static QName _SetDefaultIVRScheduleResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setDefaultIVRScheduleResponse");
    private final static QName _GetCallVariableGroupsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallVariableGroupsResponse");
    private final static QName _InvalidAccountFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "InvalidAccountFault");
    private final static QName _GetCampaignProfileDispositions_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignProfileDispositions");
    private final static QName _DeleteCallVariablesGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCallVariablesGroupResponse");
    private final static QName _ModifyUser_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUser");
    private final static QName _DispositionAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DispositionAlreadyExistsFault");
    private final static QName _AddSkillsToCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addSkillsToCampaign");
    private final static QName _ModifyPromptWavResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyPromptWavResponse");
    private final static QName _CreateSkill_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createSkill");
    private final static QName _ResetListPosition_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "resetListPosition");
    private final static QName _DeleteWebConnector_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteWebConnector");
    private final static QName _IsReportRunning_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "isReportRunning");
    private final static QName _CreateUserResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createUserResponse");
    private final static QName _DeleteReasonCodeResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteReasonCodeResponse");
    private final static QName _DeleteFromListCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromListCsvResponse");
    private final static QName _RunReport_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "runReport");
    private final static QName _ServiceUnavailableFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ServiceUnavailableFault");
    private final static QName _DeleteContactFieldResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteContactFieldResponse");
    private final static QName _CreateAutodialCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createAutodialCampaign");
    private final static QName _ResetCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "resetCampaign");
    private final static QName _GetCampaignProfileFilterResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignProfileFilterResponse");
    private final static QName _GetContactFieldsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getContactFieldsResponse");
    private final static QName _ModifySkill_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifySkill");
    private final static QName _UpdateCrmRecordResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateCrmRecordResponse");
    private final static QName _CreateIVRScriptResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createIVRScriptResponse");
    private final static QName _ModifyAutodialCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyAutodialCampaign");
    private final static QName _ListNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ListNotFoundFault");
    private final static QName _GetUserProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserProfile");
    private final static QName _GetContactRecordsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getContactRecordsResponse");
    private final static QName _UpdateDispositionsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateDispositionsResponse");
    private final static QName _SetDialingRules_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setDialingRules");
    private final static QName _ModifyContactFieldResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyContactFieldResponse");
    private final static QName _FinderException_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "FinderException");
    private final static QName _CampaignNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "CampaignNotFoundFault");
    private final static QName _UserHasNoRequiredRoleFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserHasNoRequiredRoleFault");
    private final static QName _ModifyPromptWavInline_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyPromptWavInline");
    private final static QName _ParseException_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ParseException");
    private final static QName _DeleteFromContactsCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromContactsCsvResponse");
    private final static QName _UserSkillAdd_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "userSkillAdd");
    private final static QName _ImportCancelledFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ImportCancelledFault");
    private final static QName _AddPromptWavInlineResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addPromptWavInlineResponse");
    private final static QName _DeleteRecordFromListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteRecordFromListResponse");
    private final static QName _GetCrmImportResultResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCrmImportResultResponse");
    private final static QName _GetSkillsInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillsInfoResponse");
    private final static QName _ResetCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "resetCampaignResponse");
    private final static QName _GetSkillResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillResponse");
    private final static QName _UserSkillRemove_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "userSkillRemove");
    private final static QName _ModifyPromptWavInlineResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyPromptWavInlineResponse");
    private final static QName _ObjectNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ObjectNotFoundFault");
    private final static QName _GetCallLogReportCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallLogReportCsvResponse");
    private final static QName _CreateAgentGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createAgentGroup");
    private final static QName _GetAutodialCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAutodialCampaignResponse");
    private final static QName _ModifyPromptTTS_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyPromptTTS");
    private final static QName _UpdateDispositions_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateDispositions");
    private final static QName _IvrScriptNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "IvrScriptNotFoundFault");
    private final static QName _DeleteFromContactsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromContactsResponse");
    private final static QName _ObjectsCountLimitExceededFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ObjectsCountLimitExceededFault");
    private final static QName _GetDispositionsImportResult_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDispositionsImportResult");
    private final static QName _GetReasonCodeByTypeResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReasonCodeByTypeResponse");
    private final static QName _DeleteList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteList");
    private final static QName _CreateContactFieldResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createContactFieldResponse");
    private final static QName _AddListsToCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addListsToCampaign");
    private final static QName _CreateOutboundCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createOutboundCampaign");
    private final static QName _GetUserGeneralInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserGeneralInfo");
    private final static QName _PromptAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "PromptAlreadyExistsFault");
    private final static QName _GetCallLogReportResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallLogReportResponse");
    private final static QName _GetSkillsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillsResponse");
    private final static QName _CreateSkillResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createSkillResponse");
    private final static QName _InvalidRegexpPatternFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "InvalidRegexpPatternFault");
    private final static QName _DispositionIsAlreadyAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DispositionIsAlreadyAssignedFault");
    private final static QName _ModifyIVRScript_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyIVRScript");
    private final static QName _RemoveSkillsFromCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeSkillsFromCampaign");
    private final static QName _ObjectAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ObjectAlreadyExistsFault");
    private final static QName _DeleteIVRScriptResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteIVRScriptResponse");
    private final static QName _UpdateDispositionsFtpResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateDispositionsFtpResponse");
    private final static QName _GetCallVariables_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallVariables");
    private final static QName _GetCampaignsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignsResponse");
    private final static QName _SkillAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "SkillAlreadyExistsFault");
    private final static QName _RemoveDNISFromCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeDNISFromCampaignResponse");
    private final static QName _ModifyCampaignListsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignListsResponse");
    private final static QName _GetReasonCodeResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReasonCodeResponse");
    private final static QName _CreateUserProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createUserProfileResponse");
    private final static QName _ModifyAutodialCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyAutodialCampaignResponse");
    private final static QName _DeleteFromContacts_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromContacts");
    private final static QName _UpdateCrmRecord_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateCrmRecord");
    private final static QName _CreateUserProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createUserProfile");
    private final static QName _ForceStopCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "forceStopCampaignResponse");
    private final static QName _InvalidImportDataFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "InvalidImportDataFault");
    private final static QName _MissedOsLoginFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "MissedOsLoginFault");
    private final static QName _ModifyUserResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserResponse");
    private final static QName _RenameDispositionResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "renameDispositionResponse");
    private final static QName _CreateAgentGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createAgentGroupResponse");
    private final static QName _GetAgentGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentGroup");
    private final static QName _ModifyCampaignProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileResponse");
    private final static QName _DeleteUserResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteUserResponse");
    private final static QName _IsImportRunningResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "isImportRunningResponse");
    private final static QName _DeleteCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCampaignResponse");
    private final static QName _GetIVRScriptsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getIVRScriptsResponse");
    private final static QName _UserSkillAddResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "userSkillAddResponse");
    private final static QName _GetCrmImportResult_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCrmImportResult");
    private final static QName _CreateListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createListResponse");
    private final static QName _UpdateContactsFtp_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateContactsFtp");
    private final static QName _CreateList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createList");
    private final static QName _DeleteCampaignProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCampaignProfile");
    private final static QName _ModifyUserProfileUserList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserProfileUserList");
    private final static QName _AddRecordToList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addRecordToList");
    private final static QName _GetUserProfilesResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserProfilesResponse");
    private final static QName _ModifyCallVariableResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCallVariableResponse");
    private final static QName _SetDefaultIVRSchedule_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setDefaultIVRSchedule");
    private final static QName _DNISNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DNISNotFoundFault");
    private final static QName _IncorrectArgumentFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "IncorrectArgumentFault");
    private final static QName _ModifyCampaignProfileDispositionsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileDispositionsResponse");
    private final static QName _GetDNISList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDNISList");
    private final static QName _GetContactRecords_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getContactRecords");
    private final static QName _AsyncAddRecordsToList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "asyncAddRecordsToList");
    private final static QName _DeleteRecordFromList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteRecordFromList");
    private final static QName _ServerFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ServerFault");
    private final static QName _AddDispositionsToCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addDispositionsToCampaignResponse");
    private final static QName _ContactsLookupResult_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "contactsLookupResult");
    private final static QName _ModifyCampaignProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfile");
    private final static QName _WavFileUploadFailedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "WavFileUploadFailedFault");
    private final static QName _ModifyDisposition_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyDisposition");
    private final static QName _DeleteAgentGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteAgentGroup");
    private final static QName _UpdateContactsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateContactsResponse");
    private final static QName _CreateWebConnectorResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createWebConnectorResponse");
    private final static QName _DeleteWebConnectorResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteWebConnectorResponse");
    private final static QName _GetDNISListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDNISListResponse");
    private final static QName _GetCampaignStateResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignStateResponse");
    private final static QName _GetCallVariableGroups_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallVariableGroups");
    private final static QName _AddRecordToListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addRecordToListResponse");
    private final static QName _InvalidDateRangeFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "InvalidDateRangeFault");
    private final static QName _SkillNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "SkillNotFoundFault");
    private final static QName _GetDialingRules_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDialingRules");
    private final static QName _RemoveNumbersFromDncResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeNumbersFromDncResponse");
    private final static QName _GetAgentAuditReportResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentAuditReportResponse");
    private final static QName _ModifyAgentGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyAgentGroup");
    private final static QName _GetListImportResultResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getListImportResultResponse");
    private final static QName _ModifyUserCannedReportsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserCannedReportsResponse");
    private final static QName _CreateCallVariablesGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createCallVariablesGroupResponse");
    private final static QName _LogoutReasonCodeNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "LogoutReasonCodeNotFoundFault");
    private final static QName _AddSkillsToCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addSkillsToCampaignResponse");
    private final static QName _GetCampaignState_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignState");
    private final static QName _GetListsForCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getListsForCampaignResponse");
    private final static QName _GetUserInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserInfo");
    private final static QName _AsyncDeleteRecordsFromListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "asyncDeleteRecordsFromListResponse");
    private final static QName _ModifyInboundCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyInboundCampaignResponse");
    private final static QName _RenameDisposition_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "renameDisposition");
    private final static QName _DeleteFromListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromListResponse");
    private final static QName _GetOutboundCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getOutboundCampaignResponse");
    private final static QName _GetAgentAuditReport_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentAuditReport");
    private final static QName _CreateContactField_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createContactField");
    private final static QName _ModifyCampaignProfileFilterOrder_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileFilterOrder");
    private final static QName _GetDispositionResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDispositionResponse");
    private final static QName _ListIsNotAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ListIsNotAssignedFault");
    private final static QName _ListAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ListAlreadyExistsFault");
    private final static QName _ModifyUserProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserProfileResponse");
    private final static QName _GetVCCConfigurationResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getVCCConfigurationResponse");
    private final static QName _GetVCCConfiguration_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getVCCConfiguration");
    private final static QName _UpdateDispositionsFtp_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateDispositionsFtp");
    private final static QName _GetDisposition_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDisposition");
    private final static QName _GetUserVoicemailGreeting_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserVoicemailGreeting");
    private final static QName _UpdateDispositionsCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateDispositionsCsv");
    private final static QName _CreateReasonCode_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createReasonCode");
    private final static QName _WrongPromptTypeFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "WrongPromptTypeFault");
    private final static QName _DNISAlreadyAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DNISAlreadyAssignedFault");
    private final static QName _MissedArgumentFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "MissedArgumentFault");
    private final static QName _AddNumbersToDncResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addNumbersToDncResponse");
    private final static QName _GetAutodialCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAutodialCampaign");
    private final static QName _RemoveSkillsFromCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeSkillsFromCampaignResponse");
    private final static QName _AddToListFtpResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addToListFtpResponse");
    private final static QName _GetDialingRulesResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDialingRulesResponse");
    private final static QName _GetCallLogReport_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallLogReport");
    private final static QName _AccessDisallowedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "AccessDisallowedFault");
    private final static QName _AddPromptWavResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addPromptWavResponse");
    private final static QName _RemoveDispositionResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeDispositionResponse");
    private final static QName _ModifyPromptWav_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyPromptWav");
    private final static QName _ModifyOutboundCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyOutboundCampaignResponse");
    private final static QName _ModifyUserProfileSkills_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserProfileSkills");
    private final static QName _UpdateContactsFtpResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateContactsFtpResponse");
    private final static QName _CreateAutodialCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createAutodialCampaignResponse");
    private final static QName _StopCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "stopCampaignResponse");
    private final static QName _ModifyPromptTTSResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyPromptTTSResponse");
    private final static QName _AddToList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addToList");
    private final static QName _UserCantBeDeletedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserCantBeDeletedFault");
    private final static QName _RemoveListsFromCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeListsFromCampaign");
    private final static QName _ResultIsNotReadyFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ResultIsNotReadyFault");
    private final static QName _RemoveListsFromCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeListsFromCampaignResponse");
    private final static QName _ModifyVCCConfiguration_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyVCCConfiguration");
    private final static QName _AddToListCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addToListCsv");
    private final static QName _CreateInboundCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createInboundCampaign");
    private final static QName _GetListsInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getListsInfoResponse");
    private final static QName _UserAlreadyLoggedInFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserAlreadyLoggedInFault");
    private final static QName _ReasonCodeCountLimitExceededFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ReasonCodeCountLimitExceededFault");
    private final static QName _ModifyIVRScriptResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyIVRScriptResponse");
    private final static QName _DeleteCampaignProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCampaignProfileResponse");
    private final static QName _CreateReasonCodeResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createReasonCodeResponse");
    private final static QName _GetReportResultResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReportResultResponse");
    private final static QName _ReportNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ReportNotFoundFault");
    private final static QName _GetPromptResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getPromptResponse");
    private final static QName _ExtensionAlreadyInUseFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ExtensionAlreadyInUseFault");
    private final static QName _AddToListFtp_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addToListFtp");
    private final static QName _DeleteFromListFtpResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromListFtpResponse");
    private final static QName _ScheduleNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ScheduleNotFoundFault");
    private final static QName _DeleteListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteListResponse");
    private final static QName _ExecutionRestrictionFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ExecutionRestrictionFault");
    private final static QName _DispositionIsNotAssisgnedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DispositionIsNotAssisgnedFault");
    private final static QName _ModifyUserProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserProfile");
    private final static QName _DeleteAllFromList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteAllFromList");
    private final static QName _GetInboundCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getInboundCampaign");
    private final static QName _CreateWebConnector_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createWebConnector");
    private final static QName _ListCantBeRemovedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ListCantBeRemovedFault");
    private final static QName _ModifyDispositionResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyDispositionResponse");
    private final static QName _GetUsersInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUsersInfo");
    private final static QName _CantModifyObjectFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "CantModifyObjectFault");
    private final static QName _CampaignStateUpdateFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "CampaignStateUpdateFault");
    private final static QName _SetDialingRulesResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setDialingRulesResponse");
    private final static QName _ModifyCampaignProfileDispositions_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileDispositions");
    private final static QName _RemoveDNISFromCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeDNISFromCampaign");
    private final static QName _DeleteFromListCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromListCsv");
    private final static QName _DeleteCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCampaign");
    private final static QName _UserDoesntHaveSkillFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserDoesntHaveSkillFault");
    private final static QName _AddPromptWavInline_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addPromptWavInline");
    private final static QName _IsImportRunning_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "isImportRunning");
    private final static QName _DeleteFromListFtp_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromListFtp");
    private final static QName _CreateCallVariable_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createCallVariable");
    private final static QName _CheckDncForNumbersResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "checkDncForNumbersResponse");
    private final static QName _GetReasonCode_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReasonCode");
    private final static QName _CreateDisposition_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createDisposition");
    private final static QName _GetUserVoicemailGreetingResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserVoicemailGreetingResponse");
    private final static QName _CrmFieldNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "CrmFieldNotFoundFault");
    private final static QName _GetReportResultCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReportResultCsvResponse");
    private final static QName _GetDispositionsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDispositionsResponse");
    private final static QName _DeleteUserProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteUserProfile");
    private final static QName _ObjectInUseFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ObjectInUseFault");
    private final static QName _DeleteReasonCode_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteReasonCode");
    private final static QName _GetReportResultCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReportResultCsv");
    private final static QName _DeleteCallVariablesGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCallVariablesGroup");
    private final static QName _ModifyInboundCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyInboundCampaign");
    private final static QName _SetSkillVoicemailGreetingResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setSkillVoicemailGreetingResponse");
    private final static QName _RenameCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "renameCampaignResponse");
    private final static QName _AsyncUpdateCrmRecordsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "asyncUpdateCrmRecordsResponse");
    private final static QName _GetAgentGroupsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentGroupsResponse");
    private final static QName _ModifyWebConnector_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyWebConnector");
    private final static QName _StopCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "stopCampaign");
    private final static QName _GetAgentGroups_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentGroups");
    private final static QName _GetSkillInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillInfoResponse");
    private final static QName _DeleteUserProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteUserProfileResponse");
    private final static QName _SetUserVoicemailGreetingResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setUserVoicemailGreetingResponse");
    private final static QName _ResetCampaignDispositionsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "resetCampaignDispositionsResponse");
    private final static QName _GetSkills_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkills");
    private final static QName _GetCampaignProfilesResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignProfilesResponse");
    private final static QName _DeletePromptResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deletePromptResponse");
    private final static QName _TooManyExtensionsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "TooManyExtensionsFault");
    private final static QName _DeleteSkill_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteSkill");
    private final static QName _ListAlreadyAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ListAlreadyAssignedFault");
    private final static QName _GetContactFields_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getContactFields");
    private final static QName _PromptNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "PromptNotFoundFault");
    private final static QName _ModifyAgentGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyAgentGroupResponse");
    private final static QName _GetUserGeneralInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserGeneralInfoResponse");
    private final static QName _StartCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "startCampaign");
    private final static QName _StartCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "startCampaignResponse");
    private final static QName _ImportInProgressFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ImportInProgressFault");
    private final static QName _AsyncDeleteRecordsFromList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "asyncDeleteRecordsFromList");
    private final static QName _GetUserProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserProfileResponse");
    private final static QName _ModifyCallVariablesGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCallVariablesGroupResponse");
    private final static QName _GetListsInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getListsInfo");
    private final static QName _NotReadyReasonCodeNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "NotReadyReasonCodeNotFoundFault");
    private final static QName _RemoveNumbersFromDnc_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeNumbersFromDnc");
    private final static QName _UserAlreadyHasSkillFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserAlreadyHasSkillFault");
    private final static QName _GetCampaigns_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaigns");
    private final static QName _CreateCallVariableResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createCallVariableResponse");
    private final static QName _ModifyCallVariable_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCallVariable");
    private final static QName _GetAgentAuditReportCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentAuditReportCsvResponse");
    private final static QName _GetUsersGeneralInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUsersGeneralInfoResponse");
    private final static QName _GetOutboundCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getOutboundCampaign");
    private final static QName _GetCampaignProfileDispositionsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignProfileDispositionsResponse");
    private final static QName _UpdateContactsCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateContactsCsv");
    private final static QName _CreateInboundCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createInboundCampaignResponse");
    private final static QName _GetCampaignDNISList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignDNISList");
    private final static QName _ModifyContactField_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyContactField");
    private final static QName _GetInboundCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getInboundCampaignResponse");
    private final static QName _CreateCampaignProfile_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createCampaignProfile");
    private final static QName _SetUserVoicemailGreeting_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setUserVoicemailGreeting");
    private final static QName _GetCallVariablesResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallVariablesResponse");
    private final static QName _UserNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserNotFoundFault");
    private final static QName _GetWebConnectors_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getWebConnectors");
    private final static QName _ModifyReasonCode_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyReasonCode");
    private final static QName _AddPromptTTSResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addPromptTTSResponse");
    private final static QName _ReasonCodeNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ReasonCodeNotFoundFault");
    private final static QName _UpdateContactsCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateContactsCsvResponse");
    private final static QName _DeleteAllFromListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteAllFromListResponse");
    private final static QName _GetIVRScripts_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getIVRScripts");
    private final static QName _InternalImportFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "InternalImportFault");
    private final static QName _RemoveDispositionsFromCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeDispositionsFromCampaign");
    private final static QName _GetSkillVoicemailGreetingResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillVoicemailGreetingResponse");
    private final static QName _SkillAlreadyAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "SkillAlreadyAssignedFault");
    private final static QName _ModifyCampaignProfileCrmCriteriaResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileCrmCriteriaResponse");
    private final static QName _GetCampaignProfiles_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCampaignProfiles");
    private final static QName _AddDNISToCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addDNISToCampaignResponse");
    private final static QName _DeleteCallVariable_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCallVariable");
    private final static QName _InvalidUserDataFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "InvalidUserDataFault");
    private final static QName _GetAgentAuditReportCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentAuditReportCsv");
    private final static QName _ModifyWebConnectorResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyWebConnectorResponse");
    private final static QName _GetAgentGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getAgentGroupResponse");
    private final static QName _GetPrompt_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getPrompt");
    private final static QName _AddToListCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addToListCsvResponse");
    private final static QName _ScheduleOperationFailedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ScheduleOperationFailedFault");
    private final static QName _DNISIsNotAssignedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DNISIsNotAssignedFault");
    private final static QName _CampaignAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "CampaignAlreadyExistsFault");
    private final static QName _IsReportRunningResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "isReportRunningResponse");
    private final static QName _GetListImportResult_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getListImportResult");
    private final static QName _CreateOutboundCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createOutboundCampaignResponse");
    private final static QName _AddDNISToCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addDNISToCampaign");
    private final static QName _DialProfileNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DialProfileNotFoundFault");
    private final static QName _UserAlreadyExistsFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UserAlreadyExistsFault");
    private final static QName _DeleteFromContactsCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromContactsCsv");
    private final static QName _ResetCampaignDispositions_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "resetCampaignDispositions");
    private final static QName _GetUserInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserInfoResponse");
    private final static QName _GetUsersGeneralInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUsersGeneralInfo");
    private final static QName _DispositionNotAllowedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DispositionNotAllowedFault");
    private final static QName _PromptCantBeDeletedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "PromptCantBeDeletedFault");
    private final static QName _ResetListPositionResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "resetListPositionResponse");
    private final static QName _UnknownIdentifierFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "UnknownIdentifierFault");
    private final static QName _DeleteCallVariableResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteCallVariableResponse");
    private final static QName _DeleteReasonCodeByTypeResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteReasonCodeByTypeResponse");
    private final static QName _DispositionNotFoundFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "DispositionNotFoundFault");
    private final static QName _UpdateDispositionsCsvResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateDispositionsCsvResponse");
    private final static QName _TtsGenerationFailed_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "TtsGenerationFailed");
    private final static QName _AdminSessionClosedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "AdminSessionClosedFault");
    private final static QName _RunReportResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "runReportResponse");
    private final static QName _AddToListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addToListResponse");
    private final static QName _ConcurrentModificationFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "ConcurrentModificationFault");
    private final static QName _OperationsLimitExceededFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "OperationsLimitExceededFault");
    private final static QName _ModifyUserProfileUserListResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserProfileUserListResponse");
    private final static QName _GetWebConnectorsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getWebConnectorsResponse");
    private final static QName _WrongCampaignStateFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "WrongCampaignStateFault");
    private final static QName _AsyncUpdateCrmRecords_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "asyncUpdateCrmRecords");
    private final static QName _NICELicencesExceededFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "NICELicencesExceededFault");
    private final static QName _SetSkillVoicemailGreeting_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "setSkillVoicemailGreeting");
    private final static QName _GetSkillVoicemailGreeting_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillVoicemailGreeting");
    private final static QName _ModifyCampaignProfileCrmCriteria_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCampaignProfileCrmCriteria");
    private final static QName _ModifyCallVariablesGroup_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyCallVariablesGroup");
    private final static QName _CreateIVRScript_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createIVRScript");
    private final static QName _DeleteAgentGroupResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteAgentGroupResponse");
    private final static QName _GetReportResult_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getReportResult");
    private final static QName _ForceStopCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "forceStopCampaign");
    private final static QName _RenameCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "renameCampaign");
    private final static QName _GetUsersInfoResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUsersInfoResponse");
    private final static QName _ModifyOutboundCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyOutboundCampaign");
    private final static QName _WrongCampaignTypeFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "WrongCampaignTypeFault");
    private final static QName _DeleteIVRScript_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteIVRScript");
    private final static QName _DeleteFromList_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromList");
    private final static QName _DeleteFromContactsFtp_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteFromContactsFtp");
    private final static QName _TooManyUsersFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "TooManyUsersFault");
    private final static QName _AddPromptTTS_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addPromptTTS");
    private final static QName _UpdateContacts_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "updateContacts");
    private final static QName _CheckDncForNumbers_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "checkDncForNumbers");
    private final static QName _GetDispositions_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDispositions");
    private final static QName _ModifyUserProfileSkillsResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyUserProfileSkillsResponse");
    private final static QName _AddPromptWav_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addPromptWav");
    private final static QName _GetListsForCampaign_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getListsForCampaign");
    private final static QName _GetSkillsInfo_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkillsInfo");
    private final static QName _DeleteSkillResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteSkillResponse");
    private final static QName _DeletePrompt_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deletePrompt");
    private final static QName _GetCallLogReportCsv_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getCallLogReportCsv");
    private final static QName _CreateCampaignProfileResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createCampaignProfileResponse");
    private final static QName _DeleteReasonCodeByType_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteReasonCodeByType");
    private final static QName _RemoveDisposition_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeDisposition");
    private final static QName _SkillCantBeDeletedFault_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "SkillCantBeDeletedFault");
    private final static QName _GetSkill_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getSkill");
    private final static QName _ModifyVCCConfigurationResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyVCCConfigurationResponse");
    private final static QName _ModifySkillResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifySkillResponse");
    private final static QName _ModifyReasonCodeResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "modifyReasonCodeResponse");
    private final static QName _RemoveDispositionsFromCampaignResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "removeDispositionsFromCampaignResponse");
    private final static QName _UserSkillModifyResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "userSkillModifyResponse");
    private final static QName _GetDispositionsImportResultResponse_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getDispositionsImportResultResponse");
    private final static QName _AddNumbersToDnc_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "addNumbersToDnc");
    private final static QName _CreateUser_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "createUser");
    private final static QName _DeleteUser_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "deleteUser");
    private final static QName _GetUserProfiles_QNAME = new QName("http://service.admin.ws.five9.com/v2/", "getUserProfiles");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.owd.jobs.jobobjects.five9
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BasicImportResult }
     * 
     */
    public BasicImportResult createBasicImportResult() {
        return new BasicImportResult();
    }

    /**
     * Create an instance of {@link BasicImportResult.WarningsCount }
     * 
     */
    public BasicImportResult.WarningsCount createBasicImportResultWarningsCount() {
        return new BasicImportResult.WarningsCount();
    }

    /**
     * Create an instance of {@link Record }
     * 
     */
    public Record createRecord() {
        return new Record();
    }

    /**
     * Create an instance of {@link StringArray }
     * 
     */
    public StringArray createStringArray() {
        return new StringArray();
    }

    /**
     * Create an instance of {@link CreateReasonCodeResponse }
     * 
     */
    public CreateReasonCodeResponse createCreateReasonCodeResponse() {
        return new CreateReasonCodeResponse();
    }

    /**
     * Create an instance of {@link DeleteCampaignProfileResponse }
     * 
     */
    public DeleteCampaignProfileResponse createDeleteCampaignProfileResponse() {
        return new DeleteCampaignProfileResponse();
    }

    /**
     * Create an instance of {@link ModifyIVRScriptResponse }
     * 
     */
    public ModifyIVRScriptResponse createModifyIVRScriptResponse() {
        return new ModifyIVRScriptResponse();
    }

    /**
     * Create an instance of {@link GetReportResultResponse }
     * 
     */
    public GetReportResultResponse createGetReportResultResponse() {
        return new GetReportResultResponse();
    }

    /**
     * Create an instance of {@link ReportNotFoundException }
     * 
     */
    public ReportNotFoundException createReportNotFoundException() {
        return new ReportNotFoundException();
    }

    /**
     * Create an instance of {@link AddToListFtp }
     * 
     */
    public AddToListFtp createAddToListFtp() {
        return new AddToListFtp();
    }

    /**
     * Create an instance of {@link ExtensionAlreadyInUseException }
     * 
     */
    public ExtensionAlreadyInUseException createExtensionAlreadyInUseException() {
        return new ExtensionAlreadyInUseException();
    }

    /**
     * Create an instance of {@link GetPromptResponse }
     * 
     */
    public GetPromptResponse createGetPromptResponse() {
        return new GetPromptResponse();
    }

    /**
     * Create an instance of {@link DeleteListResponse }
     * 
     */
    public DeleteListResponse createDeleteListResponse() {
        return new DeleteListResponse();
    }

    /**
     * Create an instance of {@link ScheduleNotFoundException }
     * 
     */
    public ScheduleNotFoundException createScheduleNotFoundException() {
        return new ScheduleNotFoundException();
    }

    /**
     * Create an instance of {@link DeleteFromListFtpResponse }
     * 
     */
    public DeleteFromListFtpResponse createDeleteFromListFtpResponse() {
        return new DeleteFromListFtpResponse();
    }

    /**
     * Create an instance of {@link DispositionIsNotAssignedException }
     * 
     */
    public DispositionIsNotAssignedException createDispositionIsNotAssignedException() {
        return new DispositionIsNotAssignedException();
    }

    /**
     * Create an instance of {@link ExecutionRestrictionException }
     * 
     */
    public ExecutionRestrictionException createExecutionRestrictionException() {
        return new ExecutionRestrictionException();
    }

    /**
     * Create an instance of {@link ModifyUserProfile }
     * 
     */
    public ModifyUserProfile createModifyUserProfile() {
        return new ModifyUserProfile();
    }

    /**
     * Create an instance of {@link CreateWebConnector }
     * 
     */
    public CreateWebConnector createCreateWebConnector() {
        return new CreateWebConnector();
    }

    /**
     * Create an instance of {@link DeleteAllFromList }
     * 
     */
    public DeleteAllFromList createDeleteAllFromList() {
        return new DeleteAllFromList();
    }

    /**
     * Create an instance of {@link GetInboundCampaign }
     * 
     */
    public GetInboundCampaign createGetInboundCampaign() {
        return new GetInboundCampaign();
    }

    /**
     * Create an instance of {@link ListCantBeRemovedException }
     * 
     */
    public ListCantBeRemovedException createListCantBeRemovedException() {
        return new ListCantBeRemovedException();
    }

    /**
     * Create an instance of {@link ModifyDispositionResponse }
     * 
     */
    public ModifyDispositionResponse createModifyDispositionResponse() {
        return new ModifyDispositionResponse();
    }

    /**
     * Create an instance of {@link GetUsersInfo }
     * 
     */
    public GetUsersInfo createGetUsersInfo() {
        return new GetUsersInfo();
    }

    /**
     * Create an instance of {@link ModifyPromptWav }
     * 
     */
    public ModifyPromptWav createModifyPromptWav() {
        return new ModifyPromptWav();
    }

    /**
     * Create an instance of {@link ModifyOutboundCampaignResponse }
     * 
     */
    public ModifyOutboundCampaignResponse createModifyOutboundCampaignResponse() {
        return new ModifyOutboundCampaignResponse();
    }

    /**
     * Create an instance of {@link ModifyUserProfileSkills }
     * 
     */
    public ModifyUserProfileSkills createModifyUserProfileSkills() {
        return new ModifyUserProfileSkills();
    }

    /**
     * Create an instance of {@link CreateAutodialCampaignResponse }
     * 
     */
    public CreateAutodialCampaignResponse createCreateAutodialCampaignResponse() {
        return new CreateAutodialCampaignResponse();
    }

    /**
     * Create an instance of {@link StopCampaignResponse }
     * 
     */
    public StopCampaignResponse createStopCampaignResponse() {
        return new StopCampaignResponse();
    }

    /**
     * Create an instance of {@link UpdateContactsFtpResponse }
     * 
     */
    public UpdateContactsFtpResponse createUpdateContactsFtpResponse() {
        return new UpdateContactsFtpResponse();
    }

    /**
     * Create an instance of {@link AddToList }
     * 
     */
    public AddToList createAddToList() {
        return new AddToList();
    }

    /**
     * Create an instance of {@link ModifyPromptTTSResponse }
     * 
     */
    public ModifyPromptTTSResponse createModifyPromptTTSResponse() {
        return new ModifyPromptTTSResponse();
    }

    /**
     * Create an instance of {@link RemoveListsFromCampaign }
     * 
     */
    public RemoveListsFromCampaign createRemoveListsFromCampaign() {
        return new RemoveListsFromCampaign();
    }

    /**
     * Create an instance of {@link UserCantBeDeletedException }
     * 
     */
    public UserCantBeDeletedException createUserCantBeDeletedException() {
        return new UserCantBeDeletedException();
    }

    /**
     * Create an instance of {@link ResultIsNotReadyException }
     * 
     */
    public ResultIsNotReadyException createResultIsNotReadyException() {
        return new ResultIsNotReadyException();
    }

    /**
     * Create an instance of {@link RemoveListsFromCampaignResponse }
     * 
     */
    public RemoveListsFromCampaignResponse createRemoveListsFromCampaignResponse() {
        return new RemoveListsFromCampaignResponse();
    }

    /**
     * Create an instance of {@link CreateInboundCampaign }
     * 
     */
    public CreateInboundCampaign createCreateInboundCampaign() {
        return new CreateInboundCampaign();
    }

    /**
     * Create an instance of {@link AddToListCsv }
     * 
     */
    public AddToListCsv createAddToListCsv() {
        return new AddToListCsv();
    }

    /**
     * Create an instance of {@link ModifyVCCConfiguration }
     * 
     */
    public ModifyVCCConfiguration createModifyVCCConfiguration() {
        return new ModifyVCCConfiguration();
    }

    /**
     * Create an instance of {@link UserAlreadyLoggedInException }
     * 
     */
    public UserAlreadyLoggedInException createUserAlreadyLoggedInException() {
        return new UserAlreadyLoggedInException();
    }

    /**
     * Create an instance of {@link GetListsInfoResponse }
     * 
     */
    public GetListsInfoResponse createGetListsInfoResponse() {
        return new GetListsInfoResponse();
    }

    /**
     * Create an instance of {@link ReasonCodeCountLimitExceededException }
     * 
     */
    public ReasonCodeCountLimitExceededException createReasonCodeCountLimitExceededException() {
        return new ReasonCodeCountLimitExceededException();
    }

    /**
     * Create an instance of {@link StopCampaign }
     * 
     */
    public StopCampaign createStopCampaign() {
        return new StopCampaign();
    }

    /**
     * Create an instance of {@link DeleteUserProfileResponse }
     * 
     */
    public DeleteUserProfileResponse createDeleteUserProfileResponse() {
        return new DeleteUserProfileResponse();
    }

    /**
     * Create an instance of {@link GetSkillInfoResponse }
     * 
     */
    public GetSkillInfoResponse createGetSkillInfoResponse() {
        return new GetSkillInfoResponse();
    }

    /**
     * Create an instance of {@link GetAgentGroups }
     * 
     */
    public GetAgentGroups createGetAgentGroups() {
        return new GetAgentGroups();
    }

    /**
     * Create an instance of {@link ResetCampaignDispositionsResponse }
     * 
     */
    public ResetCampaignDispositionsResponse createResetCampaignDispositionsResponse() {
        return new ResetCampaignDispositionsResponse();
    }

    /**
     * Create an instance of {@link SetUserVoicemailGreetingResponse }
     * 
     */
    public SetUserVoicemailGreetingResponse createSetUserVoicemailGreetingResponse() {
        return new SetUserVoicemailGreetingResponse();
    }

    /**
     * Create an instance of {@link GetSkills }
     * 
     */
    public GetSkills createGetSkills() {
        return new GetSkills();
    }

    /**
     * Create an instance of {@link GetCampaignProfilesResponse }
     * 
     */
    public GetCampaignProfilesResponse createGetCampaignProfilesResponse() {
        return new GetCampaignProfilesResponse();
    }

    /**
     * Create an instance of {@link TooManyExtensionsException }
     * 
     */
    public TooManyExtensionsException createTooManyExtensionsException() {
        return new TooManyExtensionsException();
    }

    /**
     * Create an instance of {@link DeletePromptResponse }
     * 
     */
    public DeletePromptResponse createDeletePromptResponse() {
        return new DeletePromptResponse();
    }

    /**
     * Create an instance of {@link PromptNotFoundException }
     * 
     */
    public PromptNotFoundException createPromptNotFoundException() {
        return new PromptNotFoundException();
    }

    /**
     * Create an instance of {@link DeleteSkill }
     * 
     */
    public DeleteSkill createDeleteSkill() {
        return new DeleteSkill();
    }

    /**
     * Create an instance of {@link ListAlreadyAssignedException }
     * 
     */
    public ListAlreadyAssignedException createListAlreadyAssignedException() {
        return new ListAlreadyAssignedException();
    }

    /**
     * Create an instance of {@link GetContactFields }
     * 
     */
    public GetContactFields createGetContactFields() {
        return new GetContactFields();
    }

    /**
     * Create an instance of {@link ModifyAgentGroupResponse }
     * 
     */
    public ModifyAgentGroupResponse createModifyAgentGroupResponse() {
        return new ModifyAgentGroupResponse();
    }

    /**
     * Create an instance of {@link GetUserGeneralInfoResponse }
     * 
     */
    public GetUserGeneralInfoResponse createGetUserGeneralInfoResponse() {
        return new GetUserGeneralInfoResponse();
    }

    /**
     * Create an instance of {@link StartCampaign }
     * 
     */
    public StartCampaign createStartCampaign() {
        return new StartCampaign();
    }

    /**
     * Create an instance of {@link StartCampaignResponse }
     * 
     */
    public StartCampaignResponse createStartCampaignResponse() {
        return new StartCampaignResponse();
    }

    /**
     * Create an instance of {@link ImportInProgressException }
     * 
     */
    public ImportInProgressException createImportInProgressException() {
        return new ImportInProgressException();
    }

    /**
     * Create an instance of {@link GetListsInfo }
     * 
     */
    public GetListsInfo createGetListsInfo() {
        return new GetListsInfo();
    }

    /**
     * Create an instance of {@link AsyncDeleteRecordsFromList }
     * 
     */
    public AsyncDeleteRecordsFromList createAsyncDeleteRecordsFromList() {
        return new AsyncDeleteRecordsFromList();
    }

    /**
     * Create an instance of {@link GetUserProfileResponse }
     * 
     */
    public GetUserProfileResponse createGetUserProfileResponse() {
        return new GetUserProfileResponse();
    }

    /**
     * Create an instance of {@link ModifyCallVariablesGroupResponse }
     * 
     */
    public ModifyCallVariablesGroupResponse createModifyCallVariablesGroupResponse() {
        return new ModifyCallVariablesGroupResponse();
    }

    /**
     * Create an instance of {@link RemoveNumbersFromDnc }
     * 
     */
    public RemoveNumbersFromDnc createRemoveNumbersFromDnc() {
        return new RemoveNumbersFromDnc();
    }

    /**
     * Create an instance of {@link NotReadyReasonCodeNotFoundException }
     * 
     */
    public NotReadyReasonCodeNotFoundException createNotReadyReasonCodeNotFoundException() {
        return new NotReadyReasonCodeNotFoundException();
    }

    /**
     * Create an instance of {@link CreateCallVariableResponse }
     * 
     */
    public CreateCallVariableResponse createCreateCallVariableResponse() {
        return new CreateCallVariableResponse();
    }

    /**
     * Create an instance of {@link GetCampaigns }
     * 
     */
    public GetCampaigns createGetCampaigns() {
        return new GetCampaigns();
    }

    /**
     * Create an instance of {@link UserAlreadyHasSkillException }
     * 
     */
    public UserAlreadyHasSkillException createUserAlreadyHasSkillException() {
        return new UserAlreadyHasSkillException();
    }

    /**
     * Create an instance of {@link CampaignStateUpdateException }
     * 
     */
    public CampaignStateUpdateException createCampaignStateUpdateException() {
        return new CampaignStateUpdateException();
    }

    /**
     * Create an instance of {@link CantModifyObjectException }
     * 
     */
    public CantModifyObjectException createCantModifyObjectException() {
        return new CantModifyObjectException();
    }

    /**
     * Create an instance of {@link SetDialingRulesResponse }
     * 
     */
    public SetDialingRulesResponse createSetDialingRulesResponse() {
        return new SetDialingRulesResponse();
    }

    /**
     * Create an instance of {@link DeleteFromListCsv }
     * 
     */
    public DeleteFromListCsv createDeleteFromListCsv() {
        return new DeleteFromListCsv();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileDispositions }
     * 
     */
    public ModifyCampaignProfileDispositions createModifyCampaignProfileDispositions() {
        return new ModifyCampaignProfileDispositions();
    }

    /**
     * Create an instance of {@link RemoveDNISFromCampaign }
     * 
     */
    public RemoveDNISFromCampaign createRemoveDNISFromCampaign() {
        return new RemoveDNISFromCampaign();
    }

    /**
     * Create an instance of {@link UserDoesntHaveSkillException }
     * 
     */
    public UserDoesntHaveSkillException createUserDoesntHaveSkillException() {
        return new UserDoesntHaveSkillException();
    }

    /**
     * Create an instance of {@link DeleteCampaign }
     * 
     */
    public DeleteCampaign createDeleteCampaign() {
        return new DeleteCampaign();
    }

    /**
     * Create an instance of {@link IsImportRunning }
     * 
     */
    public IsImportRunning createIsImportRunning() {
        return new IsImportRunning();
    }

    /**
     * Create an instance of {@link AddPromptWavInline }
     * 
     */
    public AddPromptWavInline createAddPromptWavInline() {
        return new AddPromptWavInline();
    }

    /**
     * Create an instance of {@link CreateDisposition }
     * 
     */
    public CreateDisposition createCreateDisposition() {
        return new CreateDisposition();
    }

    /**
     * Create an instance of {@link CheckDncForNumbersResponse }
     * 
     */
    public CheckDncForNumbersResponse createCheckDncForNumbersResponse() {
        return new CheckDncForNumbersResponse();
    }

    /**
     * Create an instance of {@link GetReasonCode }
     * 
     */
    public GetReasonCode createGetReasonCode() {
        return new GetReasonCode();
    }

    /**
     * Create an instance of {@link DeleteFromListFtp }
     * 
     */
    public DeleteFromListFtp createDeleteFromListFtp() {
        return new DeleteFromListFtp();
    }

    /**
     * Create an instance of {@link CreateCallVariable }
     * 
     */
    public CreateCallVariable createCreateCallVariable() {
        return new CreateCallVariable();
    }

    /**
     * Create an instance of {@link GetUserVoicemailGreetingResponse }
     * 
     */
    public GetUserVoicemailGreetingResponse createGetUserVoicemailGreetingResponse() {
        return new GetUserVoicemailGreetingResponse();
    }

    /**
     * Create an instance of {@link GetDispositionsResponse }
     * 
     */
    public GetDispositionsResponse createGetDispositionsResponse() {
        return new GetDispositionsResponse();
    }

    /**
     * Create an instance of {@link GetReportResultCsvResponse }
     * 
     */
    public GetReportResultCsvResponse createGetReportResultCsvResponse() {
        return new GetReportResultCsvResponse();
    }

    /**
     * Create an instance of {@link CrmFieldNotFoundException }
     * 
     */
    public CrmFieldNotFoundException createCrmFieldNotFoundException() {
        return new CrmFieldNotFoundException();
    }

    /**
     * Create an instance of {@link DeleteReasonCode }
     * 
     */
    public DeleteReasonCode createDeleteReasonCode() {
        return new DeleteReasonCode();
    }

    /**
     * Create an instance of {@link DeleteUserProfile }
     * 
     */
    public DeleteUserProfile createDeleteUserProfile() {
        return new DeleteUserProfile();
    }

    /**
     * Create an instance of {@link ObjectInUseException }
     * 
     */
    public ObjectInUseException createObjectInUseException() {
        return new ObjectInUseException();
    }

    /**
     * Create an instance of {@link GetReportResultCsv }
     * 
     */
    public GetReportResultCsv createGetReportResultCsv() {
        return new GetReportResultCsv();
    }

    /**
     * Create an instance of {@link SetSkillVoicemailGreetingResponse }
     * 
     */
    public SetSkillVoicemailGreetingResponse createSetSkillVoicemailGreetingResponse() {
        return new SetSkillVoicemailGreetingResponse();
    }

    /**
     * Create an instance of {@link DeleteCallVariablesGroup }
     * 
     */
    public DeleteCallVariablesGroup createDeleteCallVariablesGroup() {
        return new DeleteCallVariablesGroup();
    }

    /**
     * Create an instance of {@link ModifyInboundCampaign }
     * 
     */
    public ModifyInboundCampaign createModifyInboundCampaign() {
        return new ModifyInboundCampaign();
    }

    /**
     * Create an instance of {@link ModifyWebConnector }
     * 
     */
    public ModifyWebConnector createModifyWebConnector() {
        return new ModifyWebConnector();
    }

    /**
     * Create an instance of {@link AsyncUpdateCrmRecordsResponse }
     * 
     */
    public AsyncUpdateCrmRecordsResponse createAsyncUpdateCrmRecordsResponse() {
        return new AsyncUpdateCrmRecordsResponse();
    }

    /**
     * Create an instance of {@link GetAgentGroupsResponse }
     * 
     */
    public GetAgentGroupsResponse createGetAgentGroupsResponse() {
        return new GetAgentGroupsResponse();
    }

    /**
     * Create an instance of {@link RenameCampaignResponse }
     * 
     */
    public RenameCampaignResponse createRenameCampaignResponse() {
        return new RenameCampaignResponse();
    }

    /**
     * Create an instance of {@link GetPrompt }
     * 
     */
    public GetPrompt createGetPrompt() {
        return new GetPrompt();
    }

    /**
     * Create an instance of {@link AddToListCsvResponse }
     * 
     */
    public AddToListCsvResponse createAddToListCsvResponse() {
        return new AddToListCsvResponse();
    }

    /**
     * Create an instance of {@link DNISIsNotAssignedException }
     * 
     */
    public DNISIsNotAssignedException createDNISIsNotAssignedException() {
        return new DNISIsNotAssignedException();
    }

    /**
     * Create an instance of {@link ScheduleOperationFailedException }
     * 
     */
    public ScheduleOperationFailedException createScheduleOperationFailedException() {
        return new ScheduleOperationFailedException();
    }

    /**
     * Create an instance of {@link CampaignAlreadyExistsException }
     * 
     */
    public CampaignAlreadyExistsException createCampaignAlreadyExistsException() {
        return new CampaignAlreadyExistsException();
    }

    /**
     * Create an instance of {@link IsReportRunningResponse }
     * 
     */
    public IsReportRunningResponse createIsReportRunningResponse() {
        return new IsReportRunningResponse();
    }

    /**
     * Create an instance of {@link GetListImportResult }
     * 
     */
    public GetListImportResult createGetListImportResult() {
        return new GetListImportResult();
    }

    /**
     * Create an instance of {@link AddDNISToCampaign }
     * 
     */
    public AddDNISToCampaign createAddDNISToCampaign() {
        return new AddDNISToCampaign();
    }

    /**
     * Create an instance of {@link CreateOutboundCampaignResponse }
     * 
     */
    public CreateOutboundCampaignResponse createCreateOutboundCampaignResponse() {
        return new CreateOutboundCampaignResponse();
    }

    /**
     * Create an instance of {@link UserAlreadyExistsException }
     * 
     */
    public UserAlreadyExistsException createUserAlreadyExistsException() {
        return new UserAlreadyExistsException();
    }

    /**
     * Create an instance of {@link DialProfileNotFoundException }
     * 
     */
    public DialProfileNotFoundException createDialProfileNotFoundException() {
        return new DialProfileNotFoundException();
    }

    /**
     * Create an instance of {@link ResetCampaignDispositions }
     * 
     */
    public ResetCampaignDispositions createResetCampaignDispositions() {
        return new ResetCampaignDispositions();
    }

    /**
     * Create an instance of {@link DeleteFromContactsCsv }
     * 
     */
    public DeleteFromContactsCsv createDeleteFromContactsCsv() {
        return new DeleteFromContactsCsv();
    }

    /**
     * Create an instance of {@link GetUserInfoResponse }
     * 
     */
    public GetUserInfoResponse createGetUserInfoResponse() {
        return new GetUserInfoResponse();
    }

    /**
     * Create an instance of {@link DispositionNotAllowedException }
     * 
     */
    public DispositionNotAllowedException createDispositionNotAllowedException() {
        return new DispositionNotAllowedException();
    }

    /**
     * Create an instance of {@link GetUsersGeneralInfo }
     * 
     */
    public GetUsersGeneralInfo createGetUsersGeneralInfo() {
        return new GetUsersGeneralInfo();
    }

    /**
     * Create an instance of {@link ResetListPositionResponse }
     * 
     */
    public ResetListPositionResponse createResetListPositionResponse() {
        return new ResetListPositionResponse();
    }

    /**
     * Create an instance of {@link PromptCantBeDeletedException }
     * 
     */
    public PromptCantBeDeletedException createPromptCantBeDeletedException() {
        return new PromptCantBeDeletedException();
    }

    /**
     * Create an instance of {@link UnknownIdentifierException }
     * 
     */
    public UnknownIdentifierException createUnknownIdentifierException() {
        return new UnknownIdentifierException();
    }

    /**
     * Create an instance of {@link DeleteReasonCodeByTypeResponse }
     * 
     */
    public DeleteReasonCodeByTypeResponse createDeleteReasonCodeByTypeResponse() {
        return new DeleteReasonCodeByTypeResponse();
    }

    /**
     * Create an instance of {@link DeleteCallVariableResponse }
     * 
     */
    public DeleteCallVariableResponse createDeleteCallVariableResponse() {
        return new DeleteCallVariableResponse();
    }

    /**
     * Create an instance of {@link DispositionNotFoundException }
     * 
     */
    public DispositionNotFoundException createDispositionNotFoundException() {
        return new DispositionNotFoundException();
    }

    /**
     * Create an instance of {@link ModifyCallVariable }
     * 
     */
    public ModifyCallVariable createModifyCallVariable() {
        return new ModifyCallVariable();
    }

    /**
     * Create an instance of {@link CreateInboundCampaignResponse }
     * 
     */
    public CreateInboundCampaignResponse createCreateInboundCampaignResponse() {
        return new CreateInboundCampaignResponse();
    }

    /**
     * Create an instance of {@link UpdateContactsCsv }
     * 
     */
    public UpdateContactsCsv createUpdateContactsCsv() {
        return new UpdateContactsCsv();
    }

    /**
     * Create an instance of {@link GetCampaignProfileDispositionsResponse }
     * 
     */
    public GetCampaignProfileDispositionsResponse createGetCampaignProfileDispositionsResponse() {
        return new GetCampaignProfileDispositionsResponse();
    }

    /**
     * Create an instance of {@link GetOutboundCampaign }
     * 
     */
    public GetOutboundCampaign createGetOutboundCampaign() {
        return new GetOutboundCampaign();
    }

    /**
     * Create an instance of {@link GetUsersGeneralInfoResponse }
     * 
     */
    public GetUsersGeneralInfoResponse createGetUsersGeneralInfoResponse() {
        return new GetUsersGeneralInfoResponse();
    }

    /**
     * Create an instance of {@link GetAgentAuditReportCsvResponse }
     * 
     */
    public GetAgentAuditReportCsvResponse createGetAgentAuditReportCsvResponse() {
        return new GetAgentAuditReportCsvResponse();
    }

    /**
     * Create an instance of {@link GetCampaignDNISList }
     * 
     */
    public GetCampaignDNISList createGetCampaignDNISList() {
        return new GetCampaignDNISList();
    }

    /**
     * Create an instance of {@link GetInboundCampaignResponse }
     * 
     */
    public GetInboundCampaignResponse createGetInboundCampaignResponse() {
        return new GetInboundCampaignResponse();
    }

    /**
     * Create an instance of {@link ModifyContactField }
     * 
     */
    public ModifyContactField createModifyContactField() {
        return new ModifyContactField();
    }

    /**
     * Create an instance of {@link CreateCampaignProfile }
     * 
     */
    public CreateCampaignProfile createCreateCampaignProfile() {
        return new CreateCampaignProfile();
    }

    /**
     * Create an instance of {@link UserNotFoundException }
     * 
     */
    public UserNotFoundException createUserNotFoundException() {
        return new UserNotFoundException();
    }

    /**
     * Create an instance of {@link GetCallVariablesResponse }
     * 
     */
    public GetCallVariablesResponse createGetCallVariablesResponse() {
        return new GetCallVariablesResponse();
    }

    /**
     * Create an instance of {@link SetUserVoicemailGreeting }
     * 
     */
    public SetUserVoicemailGreeting createSetUserVoicemailGreeting() {
        return new SetUserVoicemailGreeting();
    }

    /**
     * Create an instance of {@link ReasonCodeNotFoundException }
     * 
     */
    public ReasonCodeNotFoundException createReasonCodeNotFoundException() {
        return new ReasonCodeNotFoundException();
    }

    /**
     * Create an instance of {@link AddPromptTTSResponse }
     * 
     */
    public AddPromptTTSResponse createAddPromptTTSResponse() {
        return new AddPromptTTSResponse();
    }

    /**
     * Create an instance of {@link ModifyReasonCode }
     * 
     */
    public ModifyReasonCode createModifyReasonCode() {
        return new ModifyReasonCode();
    }

    /**
     * Create an instance of {@link GetWebConnectors }
     * 
     */
    public GetWebConnectors createGetWebConnectors() {
        return new GetWebConnectors();
    }

    /**
     * Create an instance of {@link UpdateContactsCsvResponse }
     * 
     */
    public UpdateContactsCsvResponse createUpdateContactsCsvResponse() {
        return new UpdateContactsCsvResponse();
    }

    /**
     * Create an instance of {@link DeleteAllFromListResponse }
     * 
     */
    public DeleteAllFromListResponse createDeleteAllFromListResponse() {
        return new DeleteAllFromListResponse();
    }

    /**
     * Create an instance of {@link RemoveDispositionsFromCampaign }
     * 
     */
    public RemoveDispositionsFromCampaign createRemoveDispositionsFromCampaign() {
        return new RemoveDispositionsFromCampaign();
    }

    /**
     * Create an instance of {@link InternalImportException }
     * 
     */
    public InternalImportException createInternalImportException() {
        return new InternalImportException();
    }

    /**
     * Create an instance of {@link GetIVRScripts }
     * 
     */
    public GetIVRScripts createGetIVRScripts() {
        return new GetIVRScripts();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileCrmCriteriaResponse }
     * 
     */
    public ModifyCampaignProfileCrmCriteriaResponse createModifyCampaignProfileCrmCriteriaResponse() {
        return new ModifyCampaignProfileCrmCriteriaResponse();
    }

    /**
     * Create an instance of {@link SkillAlreadyAssignedException }
     * 
     */
    public SkillAlreadyAssignedException createSkillAlreadyAssignedException() {
        return new SkillAlreadyAssignedException();
    }

    /**
     * Create an instance of {@link GetSkillVoicemailGreetingResponse }
     * 
     */
    public GetSkillVoicemailGreetingResponse createGetSkillVoicemailGreetingResponse() {
        return new GetSkillVoicemailGreetingResponse();
    }

    /**
     * Create an instance of {@link GetCampaignProfiles }
     * 
     */
    public GetCampaignProfiles createGetCampaignProfiles() {
        return new GetCampaignProfiles();
    }

    /**
     * Create an instance of {@link ModifyWebConnectorResponse }
     * 
     */
    public ModifyWebConnectorResponse createModifyWebConnectorResponse() {
        return new ModifyWebConnectorResponse();
    }

    /**
     * Create an instance of {@link GetAgentAuditReportCsv }
     * 
     */
    public GetAgentAuditReportCsv createGetAgentAuditReportCsv() {
        return new GetAgentAuditReportCsv();
    }

    /**
     * Create an instance of {@link InvalidUserDataException }
     * 
     */
    public InvalidUserDataException createInvalidUserDataException() {
        return new InvalidUserDataException();
    }

    /**
     * Create an instance of {@link DeleteCallVariable }
     * 
     */
    public DeleteCallVariable createDeleteCallVariable() {
        return new DeleteCallVariable();
    }

    /**
     * Create an instance of {@link AddDNISToCampaignResponse }
     * 
     */
    public AddDNISToCampaignResponse createAddDNISToCampaignResponse() {
        return new AddDNISToCampaignResponse();
    }

    /**
     * Create an instance of {@link GetAgentGroupResponse }
     * 
     */
    public GetAgentGroupResponse createGetAgentGroupResponse() {
        return new GetAgentGroupResponse();
    }

    /**
     * Create an instance of {@link ModifyUserProfileSkillsResponse }
     * 
     */
    public ModifyUserProfileSkillsResponse createModifyUserProfileSkillsResponse() {
        return new ModifyUserProfileSkillsResponse();
    }

    /**
     * Create an instance of {@link DeleteSkillResponse }
     * 
     */
    public DeleteSkillResponse createDeleteSkillResponse() {
        return new DeleteSkillResponse();
    }

    /**
     * Create an instance of {@link GetSkillsInfo }
     * 
     */
    public GetSkillsInfo createGetSkillsInfo() {
        return new GetSkillsInfo();
    }

    /**
     * Create an instance of {@link GetListsForCampaign }
     * 
     */
    public GetListsForCampaign createGetListsForCampaign() {
        return new GetListsForCampaign();
    }

    /**
     * Create an instance of {@link AddPromptWav }
     * 
     */
    public AddPromptWav createAddPromptWav() {
        return new AddPromptWav();
    }

    /**
     * Create an instance of {@link GetCallLogReportCsv }
     * 
     */
    public GetCallLogReportCsv createGetCallLogReportCsv() {
        return new GetCallLogReportCsv();
    }

    /**
     * Create an instance of {@link DeletePrompt }
     * 
     */
    public DeletePrompt createDeletePrompt() {
        return new DeletePrompt();
    }

    /**
     * Create an instance of {@link DeleteReasonCodeByType }
     * 
     */
    public DeleteReasonCodeByType createDeleteReasonCodeByType() {
        return new DeleteReasonCodeByType();
    }

    /**
     * Create an instance of {@link CreateCampaignProfileResponse }
     * 
     */
    public CreateCampaignProfileResponse createCreateCampaignProfileResponse() {
        return new CreateCampaignProfileResponse();
    }

    /**
     * Create an instance of {@link SkillCantBeDeletedException }
     * 
     */
    public SkillCantBeDeletedException createSkillCantBeDeletedException() {
        return new SkillCantBeDeletedException();
    }

    /**
     * Create an instance of {@link RemoveDisposition }
     * 
     */
    public RemoveDisposition createRemoveDisposition() {
        return new RemoveDisposition();
    }

    /**
     * Create an instance of {@link GetSkill }
     * 
     */
    public GetSkill createGetSkill() {
        return new GetSkill();
    }

    /**
     * Create an instance of {@link ModifySkillResponse }
     * 
     */
    public ModifySkillResponse createModifySkillResponse() {
        return new ModifySkillResponse();
    }

    /**
     * Create an instance of {@link ModifyVCCConfigurationResponse }
     * 
     */
    public ModifyVCCConfigurationResponse createModifyVCCConfigurationResponse() {
        return new ModifyVCCConfigurationResponse();
    }

    /**
     * Create an instance of {@link ModifyReasonCodeResponse }
     * 
     */
    public ModifyReasonCodeResponse createModifyReasonCodeResponse() {
        return new ModifyReasonCodeResponse();
    }

    /**
     * Create an instance of {@link UserSkillModifyResponse }
     * 
     */
    public UserSkillModifyResponse createUserSkillModifyResponse() {
        return new UserSkillModifyResponse();
    }

    /**
     * Create an instance of {@link RemoveDispositionsFromCampaignResponse }
     * 
     */
    public RemoveDispositionsFromCampaignResponse createRemoveDispositionsFromCampaignResponse() {
        return new RemoveDispositionsFromCampaignResponse();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link AddNumbersToDnc }
     * 
     */
    public AddNumbersToDnc createAddNumbersToDnc() {
        return new AddNumbersToDnc();
    }

    /**
     * Create an instance of {@link GetDispositionsImportResultResponse }
     * 
     */
    public GetDispositionsImportResultResponse createGetDispositionsImportResultResponse() {
        return new GetDispositionsImportResultResponse();
    }

    /**
     * Create an instance of {@link DeleteUser }
     * 
     */
    public DeleteUser createDeleteUser() {
        return new DeleteUser();
    }

    /**
     * Create an instance of {@link GetUserProfiles }
     * 
     */
    public GetUserProfiles createGetUserProfiles() {
        return new GetUserProfiles();
    }

    /**
     * Create an instance of {@link TtsGenerationFailedException }
     * 
     */
    public TtsGenerationFailedException createTtsGenerationFailedException() {
        return new TtsGenerationFailedException();
    }

    /**
     * Create an instance of {@link UpdateDispositionsCsvResponse }
     * 
     */
    public UpdateDispositionsCsvResponse createUpdateDispositionsCsvResponse() {
        return new UpdateDispositionsCsvResponse();
    }

    /**
     * Create an instance of {@link AdminSessionClosedException }
     * 
     */
    public AdminSessionClosedException createAdminSessionClosedException() {
        return new AdminSessionClosedException();
    }

    /**
     * Create an instance of {@link RunReportResponse }
     * 
     */
    public RunReportResponse createRunReportResponse() {
        return new RunReportResponse();
    }

    /**
     * Create an instance of {@link AddToListResponse }
     * 
     */
    public AddToListResponse createAddToListResponse() {
        return new AddToListResponse();
    }

    /**
     * Create an instance of {@link ConcurrentModificationException }
     * 
     */
    public ConcurrentModificationException createConcurrentModificationException() {
        return new ConcurrentModificationException();
    }

    /**
     * Create an instance of {@link OperationsLimitExceededException }
     * 
     */
    public OperationsLimitExceededException createOperationsLimitExceededException() {
        return new OperationsLimitExceededException();
    }

    /**
     * Create an instance of {@link SetSkillVoicemailGreeting }
     * 
     */
    public SetSkillVoicemailGreeting createSetSkillVoicemailGreeting() {
        return new SetSkillVoicemailGreeting();
    }

    /**
     * Create an instance of {@link NICELicencesExceededException }
     * 
     */
    public NICELicencesExceededException createNICELicencesExceededException() {
        return new NICELicencesExceededException();
    }

    /**
     * Create an instance of {@link AsyncUpdateCrmRecords }
     * 
     */
    public AsyncUpdateCrmRecords createAsyncUpdateCrmRecords() {
        return new AsyncUpdateCrmRecords();
    }

    /**
     * Create an instance of {@link WrongCampaignStateException }
     * 
     */
    public WrongCampaignStateException createWrongCampaignStateException() {
        return new WrongCampaignStateException();
    }

    /**
     * Create an instance of {@link GetWebConnectorsResponse }
     * 
     */
    public GetWebConnectorsResponse createGetWebConnectorsResponse() {
        return new GetWebConnectorsResponse();
    }

    /**
     * Create an instance of {@link ModifyUserProfileUserListResponse }
     * 
     */
    public ModifyUserProfileUserListResponse createModifyUserProfileUserListResponse() {
        return new ModifyUserProfileUserListResponse();
    }

    /**
     * Create an instance of {@link DeleteAgentGroupResponse }
     * 
     */
    public DeleteAgentGroupResponse createDeleteAgentGroupResponse() {
        return new DeleteAgentGroupResponse();
    }

    /**
     * Create an instance of {@link CreateIVRScript }
     * 
     */
    public CreateIVRScript createCreateIVRScript() {
        return new CreateIVRScript();
    }

    /**
     * Create an instance of {@link ModifyCallVariablesGroup }
     * 
     */
    public ModifyCallVariablesGroup createModifyCallVariablesGroup() {
        return new ModifyCallVariablesGroup();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileCrmCriteria }
     * 
     */
    public ModifyCampaignProfileCrmCriteria createModifyCampaignProfileCrmCriteria() {
        return new ModifyCampaignProfileCrmCriteria();
    }

    /**
     * Create an instance of {@link GetSkillVoicemailGreeting }
     * 
     */
    public GetSkillVoicemailGreeting createGetSkillVoicemailGreeting() {
        return new GetSkillVoicemailGreeting();
    }

    /**
     * Create an instance of {@link GetUsersInfoResponse }
     * 
     */
    public GetUsersInfoResponse createGetUsersInfoResponse() {
        return new GetUsersInfoResponse();
    }

    /**
     * Create an instance of {@link RenameCampaign }
     * 
     */
    public RenameCampaign createRenameCampaign() {
        return new RenameCampaign();
    }

    /**
     * Create an instance of {@link ForceStopCampaign }
     * 
     */
    public ForceStopCampaign createForceStopCampaign() {
        return new ForceStopCampaign();
    }

    /**
     * Create an instance of {@link GetReportResult }
     * 
     */
    public GetReportResult createGetReportResult() {
        return new GetReportResult();
    }

    /**
     * Create an instance of {@link DeleteIVRScript }
     * 
     */
    public DeleteIVRScript createDeleteIVRScript() {
        return new DeleteIVRScript();
    }

    /**
     * Create an instance of {@link WrongCampaignTypeException }
     * 
     */
    public WrongCampaignTypeException createWrongCampaignTypeException() {
        return new WrongCampaignTypeException();
    }

    /**
     * Create an instance of {@link ModifyOutboundCampaign }
     * 
     */
    public ModifyOutboundCampaign createModifyOutboundCampaign() {
        return new ModifyOutboundCampaign();
    }

    /**
     * Create an instance of {@link DeleteFromList }
     * 
     */
    public DeleteFromList createDeleteFromList() {
        return new DeleteFromList();
    }

    /**
     * Create an instance of {@link DeleteFromContactsFtp }
     * 
     */
    public DeleteFromContactsFtp createDeleteFromContactsFtp() {
        return new DeleteFromContactsFtp();
    }

    /**
     * Create an instance of {@link AddPromptTTS }
     * 
     */
    public AddPromptTTS createAddPromptTTS() {
        return new AddPromptTTS();
    }

    /**
     * Create an instance of {@link TooManyUsersException }
     * 
     */
    public TooManyUsersException createTooManyUsersException() {
        return new TooManyUsersException();
    }

    /**
     * Create an instance of {@link CheckDncForNumbers }
     * 
     */
    public CheckDncForNumbers createCheckDncForNumbers() {
        return new CheckDncForNumbers();
    }

    /**
     * Create an instance of {@link GetDispositions }
     * 
     */
    public GetDispositions createGetDispositions() {
        return new GetDispositions();
    }

    /**
     * Create an instance of {@link UpdateContacts }
     * 
     */
    public UpdateContacts createUpdateContacts() {
        return new UpdateContacts();
    }

    /**
     * Create an instance of {@link DeleteWebConnector }
     * 
     */
    public DeleteWebConnector createDeleteWebConnector() {
        return new DeleteWebConnector();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link IsReportRunning }
     * 
     */
    public IsReportRunning createIsReportRunning() {
        return new IsReportRunning();
    }

    /**
     * Create an instance of {@link DeleteReasonCodeResponse }
     * 
     */
    public DeleteReasonCodeResponse createDeleteReasonCodeResponse() {
        return new DeleteReasonCodeResponse();
    }

    /**
     * Create an instance of {@link DeleteFromListCsvResponse }
     * 
     */
    public DeleteFromListCsvResponse createDeleteFromListCsvResponse() {
        return new DeleteFromListCsvResponse();
    }

    /**
     * Create an instance of {@link RunReport }
     * 
     */
    public RunReport createRunReport() {
        return new RunReport();
    }

    /**
     * Create an instance of {@link DeleteContactFieldResponse }
     * 
     */
    public DeleteContactFieldResponse createDeleteContactFieldResponse() {
        return new DeleteContactFieldResponse();
    }

    /**
     * Create an instance of {@link ServiceUnavailableException }
     * 
     */
    public ServiceUnavailableException createServiceUnavailableException() {
        return new ServiceUnavailableException();
    }

    /**
     * Create an instance of {@link GetCampaignProfileFilterResponse }
     * 
     */
    public GetCampaignProfileFilterResponse createGetCampaignProfileFilterResponse() {
        return new GetCampaignProfileFilterResponse();
    }

    /**
     * Create an instance of {@link CreateAutodialCampaign }
     * 
     */
    public CreateAutodialCampaign createCreateAutodialCampaign() {
        return new CreateAutodialCampaign();
    }

    /**
     * Create an instance of {@link ResetCampaign }
     * 
     */
    public ResetCampaign createResetCampaign() {
        return new ResetCampaign();
    }

    /**
     * Create an instance of {@link GetContactFieldsResponse }
     * 
     */
    public GetContactFieldsResponse createGetContactFieldsResponse() {
        return new GetContactFieldsResponse();
    }

    /**
     * Create an instance of {@link UpdateCrmRecordResponse }
     * 
     */
    public UpdateCrmRecordResponse createUpdateCrmRecordResponse() {
        return new UpdateCrmRecordResponse();
    }

    /**
     * Create an instance of {@link ModifySkill }
     * 
     */
    public ModifySkill createModifySkill() {
        return new ModifySkill();
    }

    /**
     * Create an instance of {@link CreateIVRScriptResponse }
     * 
     */
    public CreateIVRScriptResponse createCreateIVRScriptResponse() {
        return new CreateIVRScriptResponse();
    }

    /**
     * Create an instance of {@link ListNotFoundException }
     * 
     */
    public ListNotFoundException createListNotFoundException() {
        return new ListNotFoundException();
    }

    /**
     * Create an instance of {@link ModifyAutodialCampaign }
     * 
     */
    public ModifyAutodialCampaign createModifyAutodialCampaign() {
        return new ModifyAutodialCampaign();
    }

    /**
     * Create an instance of {@link GetUserProfile }
     * 
     */
    public GetUserProfile createGetUserProfile() {
        return new GetUserProfile();
    }

    /**
     * Create an instance of {@link UpdateDispositionsResponse }
     * 
     */
    public UpdateDispositionsResponse createUpdateDispositionsResponse() {
        return new UpdateDispositionsResponse();
    }

    /**
     * Create an instance of {@link SetDialingRules }
     * 
     */
    public SetDialingRules createSetDialingRules() {
        return new SetDialingRules();
    }

    /**
     * Create an instance of {@link GetContactRecordsResponse }
     * 
     */
    public GetContactRecordsResponse createGetContactRecordsResponse() {
        return new GetContactRecordsResponse();
    }

    /**
     * Create an instance of {@link ModifyUserCannedReports }
     * 
     */
    public ModifyUserCannedReports createModifyUserCannedReports() {
        return new ModifyUserCannedReports();
    }

    /**
     * Create an instance of {@link GetCampaignDNISListResponse }
     * 
     */
    public GetCampaignDNISListResponse createGetCampaignDNISListResponse() {
        return new GetCampaignDNISListResponse();
    }

    /**
     * Create an instance of {@link UserSkillRemoveResponse }
     * 
     */
    public UserSkillRemoveResponse createUserSkillRemoveResponse() {
        return new UserSkillRemoveResponse();
    }

    /**
     * Create an instance of {@link SkillIsNotAssignedException }
     * 
     */
    public SkillIsNotAssignedException createSkillIsNotAssignedException() {
        return new SkillIsNotAssignedException();
    }

    /**
     * Create an instance of {@link AddDispositionsToCampaign }
     * 
     */
    public AddDispositionsToCampaign createAddDispositionsToCampaign() {
        return new AddDispositionsToCampaign();
    }

    /**
     * Create an instance of {@link ModifyCampaignLists }
     * 
     */
    public ModifyCampaignLists createModifyCampaignLists() {
        return new ModifyCampaignLists();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileFilterOrderResponse }
     * 
     */
    public ModifyCampaignProfileFilterOrderResponse createModifyCampaignProfileFilterOrderResponse() {
        return new ModifyCampaignProfileFilterOrderResponse();
    }

    /**
     * Create an instance of {@link CreateDispositionResponse }
     * 
     */
    public CreateDispositionResponse createCreateDispositionResponse() {
        return new CreateDispositionResponse();
    }

    /**
     * Create an instance of {@link CreateCallVariablesGroup }
     * 
     */
    public CreateCallVariablesGroup createCreateCallVariablesGroup() {
        return new CreateCallVariablesGroup();
    }

    /**
     * Create an instance of {@link AddListsToCampaignResponse }
     * 
     */
    public AddListsToCampaignResponse createAddListsToCampaignResponse() {
        return new AddListsToCampaignResponse();
    }

    /**
     * Create an instance of {@link GetReasonCodeByType }
     * 
     */
    public GetReasonCodeByType createGetReasonCodeByType() {
        return new GetReasonCodeByType();
    }

    /**
     * Create an instance of {@link UserSkillModify }
     * 
     */
    public UserSkillModify createUserSkillModify() {
        return new UserSkillModify();
    }

    /**
     * Create an instance of {@link AsyncAddRecordsToListResponse }
     * 
     */
    public AsyncAddRecordsToListResponse createAsyncAddRecordsToListResponse() {
        return new AsyncAddRecordsToListResponse();
    }

    /**
     * Create an instance of {@link GetCampaignProfileFilter }
     * 
     */
    public GetCampaignProfileFilter createGetCampaignProfileFilter() {
        return new GetCampaignProfileFilter();
    }

    /**
     * Create an instance of {@link DeleteFromContactsFtpResponse }
     * 
     */
    public DeleteFromContactsFtpResponse createDeleteFromContactsFtpResponse() {
        return new DeleteFromContactsFtpResponse();
    }

    /**
     * Create an instance of {@link GetSkillInfo }
     * 
     */
    public GetSkillInfo createGetSkillInfo() {
        return new GetSkillInfo();
    }

    /**
     * Create an instance of {@link SetDefaultIVRScheduleResponse }
     * 
     */
    public SetDefaultIVRScheduleResponse createSetDefaultIVRScheduleResponse() {
        return new SetDefaultIVRScheduleResponse();
    }

    /**
     * Create an instance of {@link ImportRecordsCountLimitExceededException }
     * 
     */
    public ImportRecordsCountLimitExceededException createImportRecordsCountLimitExceededException() {
        return new ImportRecordsCountLimitExceededException();
    }

    /**
     * Create an instance of {@link DeleteContactField }
     * 
     */
    public DeleteContactField createDeleteContactField() {
        return new DeleteContactField();
    }

    /**
     * Create an instance of {@link GetCallVariableGroupsResponse }
     * 
     */
    public GetCallVariableGroupsResponse createGetCallVariableGroupsResponse() {
        return new GetCallVariableGroupsResponse();
    }

    /**
     * Create an instance of {@link InvalidAccountException }
     * 
     */
    public InvalidAccountException createInvalidAccountException() {
        return new InvalidAccountException();
    }

    /**
     * Create an instance of {@link GetCampaignProfileDispositions }
     * 
     */
    public GetCampaignProfileDispositions createGetCampaignProfileDispositions() {
        return new GetCampaignProfileDispositions();
    }

    /**
     * Create an instance of {@link DispositionAlreadyExistsException }
     * 
     */
    public DispositionAlreadyExistsException createDispositionAlreadyExistsException() {
        return new DispositionAlreadyExistsException();
    }

    /**
     * Create an instance of {@link ModifyUser }
     * 
     */
    public ModifyUser createModifyUser() {
        return new ModifyUser();
    }

    /**
     * Create an instance of {@link DeleteCallVariablesGroupResponse }
     * 
     */
    public DeleteCallVariablesGroupResponse createDeleteCallVariablesGroupResponse() {
        return new DeleteCallVariablesGroupResponse();
    }

    /**
     * Create an instance of {@link AddSkillsToCampaign }
     * 
     */
    public AddSkillsToCampaign createAddSkillsToCampaign() {
        return new AddSkillsToCampaign();
    }

    /**
     * Create an instance of {@link CreateSkill }
     * 
     */
    public CreateSkill createCreateSkill() {
        return new CreateSkill();
    }

    /**
     * Create an instance of {@link ResetListPosition }
     * 
     */
    public ResetListPosition createResetListPosition() {
        return new ResetListPosition();
    }

    /**
     * Create an instance of {@link ModifyPromptWavResponse }
     * 
     */
    public ModifyPromptWavResponse createModifyPromptWavResponse() {
        return new ModifyPromptWavResponse();
    }

    /**
     * Create an instance of {@link CreateSkillResponse }
     * 
     */
    public CreateSkillResponse createCreateSkillResponse() {
        return new CreateSkillResponse();
    }

    /**
     * Create an instance of {@link InvalidRegexpPatternException }
     * 
     */
    public InvalidRegexpPatternException createInvalidRegexpPatternException() {
        return new InvalidRegexpPatternException();
    }

    /**
     * Create an instance of {@link DispositionAlreadyAssignedException }
     * 
     */
    public DispositionAlreadyAssignedException createDispositionAlreadyAssignedException() {
        return new DispositionAlreadyAssignedException();
    }

    /**
     * Create an instance of {@link ModifyIVRScript }
     * 
     */
    public ModifyIVRScript createModifyIVRScript() {
        return new ModifyIVRScript();
    }

    /**
     * Create an instance of {@link RemoveSkillsFromCampaign }
     * 
     */
    public RemoveSkillsFromCampaign createRemoveSkillsFromCampaign() {
        return new RemoveSkillsFromCampaign();
    }

    /**
     * Create an instance of {@link DeleteIVRScriptResponse }
     * 
     */
    public DeleteIVRScriptResponse createDeleteIVRScriptResponse() {
        return new DeleteIVRScriptResponse();
    }

    /**
     * Create an instance of {@link ObjectAlreadyExistsException }
     * 
     */
    public ObjectAlreadyExistsException createObjectAlreadyExistsException() {
        return new ObjectAlreadyExistsException();
    }

    /**
     * Create an instance of {@link UpdateDispositionsFtpResponse }
     * 
     */
    public UpdateDispositionsFtpResponse createUpdateDispositionsFtpResponse() {
        return new UpdateDispositionsFtpResponse();
    }

    /**
     * Create an instance of {@link GetCallVariables }
     * 
     */
    public GetCallVariables createGetCallVariables() {
        return new GetCallVariables();
    }

    /**
     * Create an instance of {@link SkillAlreadyExistsException }
     * 
     */
    public SkillAlreadyExistsException createSkillAlreadyExistsException() {
        return new SkillAlreadyExistsException();
    }

    /**
     * Create an instance of {@link GetCampaignsResponse }
     * 
     */
    public GetCampaignsResponse createGetCampaignsResponse() {
        return new GetCampaignsResponse();
    }

    /**
     * Create an instance of {@link RemoveDNISFromCampaignResponse }
     * 
     */
    public RemoveDNISFromCampaignResponse createRemoveDNISFromCampaignResponse() {
        return new RemoveDNISFromCampaignResponse();
    }

    /**
     * Create an instance of {@link ModifyCampaignListsResponse }
     * 
     */
    public ModifyCampaignListsResponse createModifyCampaignListsResponse() {
        return new ModifyCampaignListsResponse();
    }

    /**
     * Create an instance of {@link GetReasonCodeResponse }
     * 
     */
    public GetReasonCodeResponse createGetReasonCodeResponse() {
        return new GetReasonCodeResponse();
    }

    /**
     * Create an instance of {@link CreateUserProfileResponse }
     * 
     */
    public CreateUserProfileResponse createCreateUserProfileResponse() {
        return new CreateUserProfileResponse();
    }

    /**
     * Create an instance of {@link DeleteFromContacts }
     * 
     */
    public DeleteFromContacts createDeleteFromContacts() {
        return new DeleteFromContacts();
    }

    /**
     * Create an instance of {@link ModifyAutodialCampaignResponse }
     * 
     */
    public ModifyAutodialCampaignResponse createModifyAutodialCampaignResponse() {
        return new ModifyAutodialCampaignResponse();
    }

    /**
     * Create an instance of {@link ForceStopCampaignResponse }
     * 
     */
    public ForceStopCampaignResponse createForceStopCampaignResponse() {
        return new ForceStopCampaignResponse();
    }

    /**
     * Create an instance of {@link CreateUserProfile }
     * 
     */
    public CreateUserProfile createCreateUserProfile() {
        return new CreateUserProfile();
    }

    /**
     * Create an instance of {@link UpdateCrmRecord }
     * 
     */
    public UpdateCrmRecord createUpdateCrmRecord() {
        return new UpdateCrmRecord();
    }

    /**
     * Create an instance of {@link ModifyContactFieldResponse }
     * 
     */
    public ModifyContactFieldResponse createModifyContactFieldResponse() {
        return new ModifyContactFieldResponse();
    }

    /**
     * Create an instance of {@link FinderException }
     * 
     */
    public FinderException createFinderException() {
        return new FinderException();
    }

    /**
     * Create an instance of {@link CampaignNotFoundException }
     * 
     */
    public CampaignNotFoundException createCampaignNotFoundException() {
        return new CampaignNotFoundException();
    }

    /**
     * Create an instance of {@link UserHasNoRequiredRolesException }
     * 
     */
    public UserHasNoRequiredRolesException createUserHasNoRequiredRolesException() {
        return new UserHasNoRequiredRolesException();
    }

    /**
     * Create an instance of {@link DeleteFromContactsCsvResponse }
     * 
     */
    public DeleteFromContactsCsvResponse createDeleteFromContactsCsvResponse() {
        return new DeleteFromContactsCsvResponse();
    }

    /**
     * Create an instance of {@link ParseException }
     * 
     */
    public ParseException createParseException() {
        return new ParseException();
    }

    /**
     * Create an instance of {@link ModifyPromptWavInline }
     * 
     */
    public ModifyPromptWavInline createModifyPromptWavInline() {
        return new ModifyPromptWavInline();
    }

    /**
     * Create an instance of {@link UserSkillAdd }
     * 
     */
    public UserSkillAdd createUserSkillAdd() {
        return new UserSkillAdd();
    }

    /**
     * Create an instance of {@link AddPromptWavInlineResponse }
     * 
     */
    public AddPromptWavInlineResponse createAddPromptWavInlineResponse() {
        return new AddPromptWavInlineResponse();
    }

    /**
     * Create an instance of {@link ImportCancelledException }
     * 
     */
    public ImportCancelledException createImportCancelledException() {
        return new ImportCancelledException();
    }

    /**
     * Create an instance of {@link GetCrmImportResultResponse }
     * 
     */
    public GetCrmImportResultResponse createGetCrmImportResultResponse() {
        return new GetCrmImportResultResponse();
    }

    /**
     * Create an instance of {@link DeleteRecordFromListResponse }
     * 
     */
    public DeleteRecordFromListResponse createDeleteRecordFromListResponse() {
        return new DeleteRecordFromListResponse();
    }

    /**
     * Create an instance of {@link GetSkillsInfoResponse }
     * 
     */
    public GetSkillsInfoResponse createGetSkillsInfoResponse() {
        return new GetSkillsInfoResponse();
    }

    /**
     * Create an instance of {@link GetSkillResponse }
     * 
     */
    public GetSkillResponse createGetSkillResponse() {
        return new GetSkillResponse();
    }

    /**
     * Create an instance of {@link ResetCampaignResponse }
     * 
     */
    public ResetCampaignResponse createResetCampaignResponse() {
        return new ResetCampaignResponse();
    }

    /**
     * Create an instance of {@link ObjectNotFoundException }
     * 
     */
    public ObjectNotFoundException createObjectNotFoundException() {
        return new ObjectNotFoundException();
    }

    /**
     * Create an instance of {@link GetCallLogReportCsvResponse }
     * 
     */
    public GetCallLogReportCsvResponse createGetCallLogReportCsvResponse() {
        return new GetCallLogReportCsvResponse();
    }

    /**
     * Create an instance of {@link ModifyPromptWavInlineResponse }
     * 
     */
    public ModifyPromptWavInlineResponse createModifyPromptWavInlineResponse() {
        return new ModifyPromptWavInlineResponse();
    }

    /**
     * Create an instance of {@link UserSkillRemove }
     * 
     */
    public UserSkillRemove createUserSkillRemove() {
        return new UserSkillRemove();
    }

    /**
     * Create an instance of {@link CreateAgentGroup }
     * 
     */
    public CreateAgentGroup createCreateAgentGroup() {
        return new CreateAgentGroup();
    }

    /**
     * Create an instance of {@link GetAutodialCampaignResponse }
     * 
     */
    public GetAutodialCampaignResponse createGetAutodialCampaignResponse() {
        return new GetAutodialCampaignResponse();
    }

    /**
     * Create an instance of {@link UpdateDispositions }
     * 
     */
    public UpdateDispositions createUpdateDispositions() {
        return new UpdateDispositions();
    }

    /**
     * Create an instance of {@link IvrScriptNotFoundException }
     * 
     */
    public IvrScriptNotFoundException createIvrScriptNotFoundException() {
        return new IvrScriptNotFoundException();
    }

    /**
     * Create an instance of {@link ModifyPromptTTS }
     * 
     */
    public ModifyPromptTTS createModifyPromptTTS() {
        return new ModifyPromptTTS();
    }

    /**
     * Create an instance of {@link GetReasonCodeByTypeResponse }
     * 
     */
    public GetReasonCodeByTypeResponse createGetReasonCodeByTypeResponse() {
        return new GetReasonCodeByTypeResponse();
    }

    /**
     * Create an instance of {@link GetDispositionsImportResult }
     * 
     */
    public GetDispositionsImportResult createGetDispositionsImportResult() {
        return new GetDispositionsImportResult();
    }

    /**
     * Create an instance of {@link DeleteFromContactsResponse }
     * 
     */
    public DeleteFromContactsResponse createDeleteFromContactsResponse() {
        return new DeleteFromContactsResponse();
    }

    /**
     * Create an instance of {@link ObjectsCountLimitExceededException }
     * 
     */
    public ObjectsCountLimitExceededException createObjectsCountLimitExceededException() {
        return new ObjectsCountLimitExceededException();
    }

    /**
     * Create an instance of {@link CreateOutboundCampaign }
     * 
     */
    public CreateOutboundCampaign createCreateOutboundCampaign() {
        return new CreateOutboundCampaign();
    }

    /**
     * Create an instance of {@link DeleteList }
     * 
     */
    public DeleteList createDeleteList() {
        return new DeleteList();
    }

    /**
     * Create an instance of {@link CreateContactFieldResponse }
     * 
     */
    public CreateContactFieldResponse createCreateContactFieldResponse() {
        return new CreateContactFieldResponse();
    }

    /**
     * Create an instance of {@link AddListsToCampaign }
     * 
     */
    public AddListsToCampaign createAddListsToCampaign() {
        return new AddListsToCampaign();
    }

    /**
     * Create an instance of {@link GetSkillsResponse }
     * 
     */
    public GetSkillsResponse createGetSkillsResponse() {
        return new GetSkillsResponse();
    }

    /**
     * Create an instance of {@link GetCallLogReportResponse }
     * 
     */
    public GetCallLogReportResponse createGetCallLogReportResponse() {
        return new GetCallLogReportResponse();
    }

    /**
     * Create an instance of {@link PromptAlreadyExistsException }
     * 
     */
    public PromptAlreadyExistsException createPromptAlreadyExistsException() {
        return new PromptAlreadyExistsException();
    }

    /**
     * Create an instance of {@link GetUserGeneralInfo }
     * 
     */
    public GetUserGeneralInfo createGetUserGeneralInfo() {
        return new GetUserGeneralInfo();
    }

    /**
     * Create an instance of {@link DNISNotFoundException }
     * 
     */
    public DNISNotFoundException createDNISNotFoundException() {
        return new DNISNotFoundException();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileDispositionsResponse }
     * 
     */
    public ModifyCampaignProfileDispositionsResponse createModifyCampaignProfileDispositionsResponse() {
        return new ModifyCampaignProfileDispositionsResponse();
    }

    /**
     * Create an instance of {@link IncorrectArgumentException }
     * 
     */
    public IncorrectArgumentException createIncorrectArgumentException() {
        return new IncorrectArgumentException();
    }

    /**
     * Create an instance of {@link GetDNISList }
     * 
     */
    public GetDNISList createGetDNISList() {
        return new GetDNISList();
    }

    /**
     * Create an instance of {@link AsyncAddRecordsToList }
     * 
     */
    public AsyncAddRecordsToList createAsyncAddRecordsToList() {
        return new AsyncAddRecordsToList();
    }

    /**
     * Create an instance of {@link GetContactRecords }
     * 
     */
    public GetContactRecords createGetContactRecords() {
        return new GetContactRecords();
    }

    /**
     * Create an instance of {@link DeleteRecordFromList }
     * 
     */
    public DeleteRecordFromList createDeleteRecordFromList() {
        return new DeleteRecordFromList();
    }

    /**
     * Create an instance of {@link ServerException }
     * 
     */
    public ServerException createServerException() {
        return new ServerException();
    }

    /**
     * Create an instance of {@link AddDispositionsToCampaignResponse }
     * 
     */
    public AddDispositionsToCampaignResponse createAddDispositionsToCampaignResponse() {
        return new AddDispositionsToCampaignResponse();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfile }
     * 
     */
    public ModifyCampaignProfile createModifyCampaignProfile() {
        return new ModifyCampaignProfile();
    }

    /**
     * Create an instance of {@link ContactsLookupResult }
     * 
     */
    public ContactsLookupResult createContactsLookupResult() {
        return new ContactsLookupResult();
    }

    /**
     * Create an instance of {@link WavFileUploadFailedException }
     * 
     */
    public WavFileUploadFailedException createWavFileUploadFailedException() {
        return new WavFileUploadFailedException();
    }

    /**
     * Create an instance of {@link ModifyDisposition }
     * 
     */
    public ModifyDisposition createModifyDisposition() {
        return new ModifyDisposition();
    }

    /**
     * Create an instance of {@link DeleteAgentGroup }
     * 
     */
    public DeleteAgentGroup createDeleteAgentGroup() {
        return new DeleteAgentGroup();
    }

    /**
     * Create an instance of {@link UpdateContactsResponse }
     * 
     */
    public UpdateContactsResponse createUpdateContactsResponse() {
        return new UpdateContactsResponse();
    }

    /**
     * Create an instance of {@link GetDNISListResponse }
     * 
     */
    public GetDNISListResponse createGetDNISListResponse() {
        return new GetDNISListResponse();
    }

    /**
     * Create an instance of {@link DeleteWebConnectorResponse }
     * 
     */
    public DeleteWebConnectorResponse createDeleteWebConnectorResponse() {
        return new DeleteWebConnectorResponse();
    }

    /**
     * Create an instance of {@link CreateWebConnectorResponse }
     * 
     */
    public CreateWebConnectorResponse createCreateWebConnectorResponse() {
        return new CreateWebConnectorResponse();
    }

    /**
     * Create an instance of {@link GetCampaignStateResponse }
     * 
     */
    public GetCampaignStateResponse createGetCampaignStateResponse() {
        return new GetCampaignStateResponse();
    }

    /**
     * Create an instance of {@link AddRecordToListResponse }
     * 
     */
    public AddRecordToListResponse createAddRecordToListResponse() {
        return new AddRecordToListResponse();
    }

    /**
     * Create an instance of {@link GetCallVariableGroups }
     * 
     */
    public GetCallVariableGroups createGetCallVariableGroups() {
        return new GetCallVariableGroups();
    }

    /**
     * Create an instance of {@link RenameDispositionResponse }
     * 
     */
    public RenameDispositionResponse createRenameDispositionResponse() {
        return new RenameDispositionResponse();
    }

    /**
     * Create an instance of {@link MissedOsLoginException }
     * 
     */
    public MissedOsLoginException createMissedOsLoginException() {
        return new MissedOsLoginException();
    }

    /**
     * Create an instance of {@link ModifyUserResponse }
     * 
     */
    public ModifyUserResponse createModifyUserResponse() {
        return new ModifyUserResponse();
    }

    /**
     * Create an instance of {@link InvalidImportDataException }
     * 
     */
    public InvalidImportDataException createInvalidImportDataException() {
        return new InvalidImportDataException();
    }

    /**
     * Create an instance of {@link GetAgentGroup }
     * 
     */
    public GetAgentGroup createGetAgentGroup() {
        return new GetAgentGroup();
    }

    /**
     * Create an instance of {@link CreateAgentGroupResponse }
     * 
     */
    public CreateAgentGroupResponse createCreateAgentGroupResponse() {
        return new CreateAgentGroupResponse();
    }

    /**
     * Create an instance of {@link DeleteUserResponse }
     * 
     */
    public DeleteUserResponse createDeleteUserResponse() {
        return new DeleteUserResponse();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileResponse }
     * 
     */
    public ModifyCampaignProfileResponse createModifyCampaignProfileResponse() {
        return new ModifyCampaignProfileResponse();
    }

    /**
     * Create an instance of {@link IsImportRunningResponse }
     * 
     */
    public IsImportRunningResponse createIsImportRunningResponse() {
        return new IsImportRunningResponse();
    }

    /**
     * Create an instance of {@link UserSkillAddResponse }
     * 
     */
    public UserSkillAddResponse createUserSkillAddResponse() {
        return new UserSkillAddResponse();
    }

    /**
     * Create an instance of {@link DeleteCampaignResponse }
     * 
     */
    public DeleteCampaignResponse createDeleteCampaignResponse() {
        return new DeleteCampaignResponse();
    }

    /**
     * Create an instance of {@link GetIVRScriptsResponse }
     * 
     */
    public GetIVRScriptsResponse createGetIVRScriptsResponse() {
        return new GetIVRScriptsResponse();
    }

    /**
     * Create an instance of {@link CreateListResponse }
     * 
     */
    public CreateListResponse createCreateListResponse() {
        return new CreateListResponse();
    }

    /**
     * Create an instance of {@link GetCrmImportResult }
     * 
     */
    public GetCrmImportResult createGetCrmImportResult() {
        return new GetCrmImportResult();
    }

    /**
     * Create an instance of {@link UpdateContactsFtp }
     * 
     */
    public UpdateContactsFtp createUpdateContactsFtp() {
        return new UpdateContactsFtp();
    }

    /**
     * Create an instance of {@link CreateList }
     * 
     */
    public CreateList createCreateList() {
        return new CreateList();
    }

    /**
     * Create an instance of {@link DeleteCampaignProfile }
     * 
     */
    public DeleteCampaignProfile createDeleteCampaignProfile() {
        return new DeleteCampaignProfile();
    }

    /**
     * Create an instance of {@link AddRecordToList }
     * 
     */
    public AddRecordToList createAddRecordToList() {
        return new AddRecordToList();
    }

    /**
     * Create an instance of {@link ModifyUserProfileUserList }
     * 
     */
    public ModifyUserProfileUserList createModifyUserProfileUserList() {
        return new ModifyUserProfileUserList();
    }

    /**
     * Create an instance of {@link ModifyCallVariableResponse }
     * 
     */
    public ModifyCallVariableResponse createModifyCallVariableResponse() {
        return new ModifyCallVariableResponse();
    }

    /**
     * Create an instance of {@link GetUserProfilesResponse }
     * 
     */
    public GetUserProfilesResponse createGetUserProfilesResponse() {
        return new GetUserProfilesResponse();
    }

    /**
     * Create an instance of {@link SetDefaultIVRSchedule }
     * 
     */
    public SetDefaultIVRSchedule createSetDefaultIVRSchedule() {
        return new SetDefaultIVRSchedule();
    }

    /**
     * Create an instance of {@link GetDispositionResponse }
     * 
     */
    public GetDispositionResponse createGetDispositionResponse() {
        return new GetDispositionResponse();
    }

    /**
     * Create an instance of {@link ListIsNotAssignedException }
     * 
     */
    public ListIsNotAssignedException createListIsNotAssignedException() {
        return new ListIsNotAssignedException();
    }

    /**
     * Create an instance of {@link ListAlreadyExistsException }
     * 
     */
    public ListAlreadyExistsException createListAlreadyExistsException() {
        return new ListAlreadyExistsException();
    }

    /**
     * Create an instance of {@link ModifyUserProfileResponse }
     * 
     */
    public ModifyUserProfileResponse createModifyUserProfileResponse() {
        return new ModifyUserProfileResponse();
    }

    /**
     * Create an instance of {@link GetVCCConfigurationResponse }
     * 
     */
    public GetVCCConfigurationResponse createGetVCCConfigurationResponse() {
        return new GetVCCConfigurationResponse();
    }

    /**
     * Create an instance of {@link GetVCCConfiguration }
     * 
     */
    public GetVCCConfiguration createGetVCCConfiguration() {
        return new GetVCCConfiguration();
    }

    /**
     * Create an instance of {@link UpdateDispositionsFtp }
     * 
     */
    public UpdateDispositionsFtp createUpdateDispositionsFtp() {
        return new UpdateDispositionsFtp();
    }

    /**
     * Create an instance of {@link GetDisposition }
     * 
     */
    public GetDisposition createGetDisposition() {
        return new GetDisposition();
    }

    /**
     * Create an instance of {@link GetUserVoicemailGreeting }
     * 
     */
    public GetUserVoicemailGreeting createGetUserVoicemailGreeting() {
        return new GetUserVoicemailGreeting();
    }

    /**
     * Create an instance of {@link CreateReasonCode }
     * 
     */
    public CreateReasonCode createCreateReasonCode() {
        return new CreateReasonCode();
    }

    /**
     * Create an instance of {@link UpdateDispositionsCsv }
     * 
     */
    public UpdateDispositionsCsv createUpdateDispositionsCsv() {
        return new UpdateDispositionsCsv();
    }

    /**
     * Create an instance of {@link DNISAlreadyAssignedException }
     * 
     */
    public DNISAlreadyAssignedException createDNISAlreadyAssignedException() {
        return new DNISAlreadyAssignedException();
    }

    /**
     * Create an instance of {@link WrongPromptTypeException }
     * 
     */
    public WrongPromptTypeException createWrongPromptTypeException() {
        return new WrongPromptTypeException();
    }

    /**
     * Create an instance of {@link MissedArgumentException }
     * 
     */
    public MissedArgumentException createMissedArgumentException() {
        return new MissedArgumentException();
    }

    /**
     * Create an instance of {@link AddNumbersToDncResponse }
     * 
     */
    public AddNumbersToDncResponse createAddNumbersToDncResponse() {
        return new AddNumbersToDncResponse();
    }

    /**
     * Create an instance of {@link GetAutodialCampaign }
     * 
     */
    public GetAutodialCampaign createGetAutodialCampaign() {
        return new GetAutodialCampaign();
    }

    /**
     * Create an instance of {@link RemoveSkillsFromCampaignResponse }
     * 
     */
    public RemoveSkillsFromCampaignResponse createRemoveSkillsFromCampaignResponse() {
        return new RemoveSkillsFromCampaignResponse();
    }

    /**
     * Create an instance of {@link AddToListFtpResponse }
     * 
     */
    public AddToListFtpResponse createAddToListFtpResponse() {
        return new AddToListFtpResponse();
    }

    /**
     * Create an instance of {@link GetDialingRulesResponse }
     * 
     */
    public GetDialingRulesResponse createGetDialingRulesResponse() {
        return new GetDialingRulesResponse();
    }

    /**
     * Create an instance of {@link AccessDisallowedException }
     * 
     */
    public AccessDisallowedException createAccessDisallowedException() {
        return new AccessDisallowedException();
    }

    /**
     * Create an instance of {@link GetCallLogReport }
     * 
     */
    public GetCallLogReport createGetCallLogReport() {
        return new GetCallLogReport();
    }

    /**
     * Create an instance of {@link RemoveDispositionResponse }
     * 
     */
    public RemoveDispositionResponse createRemoveDispositionResponse() {
        return new RemoveDispositionResponse();
    }

    /**
     * Create an instance of {@link AddPromptWavResponse }
     * 
     */
    public AddPromptWavResponse createAddPromptWavResponse() {
        return new AddPromptWavResponse();
    }

    /**
     * Create an instance of {@link SkillNotFoundException }
     * 
     */
    public SkillNotFoundException createSkillNotFoundException() {
        return new SkillNotFoundException();
    }

    /**
     * Create an instance of {@link InvalidDateRangeException }
     * 
     */
    public InvalidDateRangeException createInvalidDateRangeException() {
        return new InvalidDateRangeException();
    }

    /**
     * Create an instance of {@link RemoveNumbersFromDncResponse }
     * 
     */
    public RemoveNumbersFromDncResponse createRemoveNumbersFromDncResponse() {
        return new RemoveNumbersFromDncResponse();
    }

    /**
     * Create an instance of {@link GetDialingRules }
     * 
     */
    public GetDialingRules createGetDialingRules() {
        return new GetDialingRules();
    }

    /**
     * Create an instance of {@link ModifyAgentGroup }
     * 
     */
    public ModifyAgentGroup createModifyAgentGroup() {
        return new ModifyAgentGroup();
    }

    /**
     * Create an instance of {@link GetAgentAuditReportResponse }
     * 
     */
    public GetAgentAuditReportResponse createGetAgentAuditReportResponse() {
        return new GetAgentAuditReportResponse();
    }

    /**
     * Create an instance of {@link GetListImportResultResponse }
     * 
     */
    public GetListImportResultResponse createGetListImportResultResponse() {
        return new GetListImportResultResponse();
    }

    /**
     * Create an instance of {@link LogoutReasonCodeNotFoundException }
     * 
     */
    public LogoutReasonCodeNotFoundException createLogoutReasonCodeNotFoundException() {
        return new LogoutReasonCodeNotFoundException();
    }

    /**
     * Create an instance of {@link CreateCallVariablesGroupResponse }
     * 
     */
    public CreateCallVariablesGroupResponse createCreateCallVariablesGroupResponse() {
        return new CreateCallVariablesGroupResponse();
    }

    /**
     * Create an instance of {@link ModifyUserCannedReportsResponse }
     * 
     */
    public ModifyUserCannedReportsResponse createModifyUserCannedReportsResponse() {
        return new ModifyUserCannedReportsResponse();
    }

    /**
     * Create an instance of {@link AddSkillsToCampaignResponse }
     * 
     */
    public AddSkillsToCampaignResponse createAddSkillsToCampaignResponse() {
        return new AddSkillsToCampaignResponse();
    }

    /**
     * Create an instance of {@link GetListsForCampaignResponse }
     * 
     */
    public GetListsForCampaignResponse createGetListsForCampaignResponse() {
        return new GetListsForCampaignResponse();
    }

    /**
     * Create an instance of {@link GetCampaignState }
     * 
     */
    public GetCampaignState createGetCampaignState() {
        return new GetCampaignState();
    }

    /**
     * Create an instance of {@link GetUserInfo }
     * 
     */
    public GetUserInfo createGetUserInfo() {
        return new GetUserInfo();
    }

    /**
     * Create an instance of {@link ModifyInboundCampaignResponse }
     * 
     */
    public ModifyInboundCampaignResponse createModifyInboundCampaignResponse() {
        return new ModifyInboundCampaignResponse();
    }

    /**
     * Create an instance of {@link AsyncDeleteRecordsFromListResponse }
     * 
     */
    public AsyncDeleteRecordsFromListResponse createAsyncDeleteRecordsFromListResponse() {
        return new AsyncDeleteRecordsFromListResponse();
    }

    /**
     * Create an instance of {@link RenameDisposition }
     * 
     */
    public RenameDisposition createRenameDisposition() {
        return new RenameDisposition();
    }

    /**
     * Create an instance of {@link GetAgentAuditReport }
     * 
     */
    public GetAgentAuditReport createGetAgentAuditReport() {
        return new GetAgentAuditReport();
    }

    /**
     * Create an instance of {@link GetOutboundCampaignResponse }
     * 
     */
    public GetOutboundCampaignResponse createGetOutboundCampaignResponse() {
        return new GetOutboundCampaignResponse();
    }

    /**
     * Create an instance of {@link DeleteFromListResponse }
     * 
     */
    public DeleteFromListResponse createDeleteFromListResponse() {
        return new DeleteFromListResponse();
    }

    /**
     * Create an instance of {@link CreateContactField }
     * 
     */
    public CreateContactField createCreateContactField() {
        return new CreateContactField();
    }

    /**
     * Create an instance of {@link ModifyCampaignProfileFilterOrder }
     * 
     */
    public ModifyCampaignProfileFilterOrder createModifyCampaignProfileFilterOrder() {
        return new ModifyCampaignProfileFilterOrder();
    }

    /**
     * Create an instance of {@link ReportingRole }
     * 
     */
    public ReportingRole createReportingRole() {
        return new ReportingRole();
    }

    /**
     * Create an instance of {@link RemoteHostLoginSettings }
     * 
     */
    public RemoteHostLoginSettings createRemoteHostLoginSettings() {
        return new RemoteHostLoginSettings();
    }

    /**
     * Create an instance of {@link AutodialCampaign }
     * 
     */
    public AutodialCampaign createAutodialCampaign() {
        return new AutodialCampaign();
    }

    /**
     * Create an instance of {@link CampaignProfileInfo }
     * 
     */
    public CampaignProfileInfo createCampaignProfileInfo() {
        return new CampaignProfileInfo();
    }

    /**
     * Create an instance of {@link RecordData }
     * 
     */
    public RecordData createRecordData() {
        return new RecordData();
    }

    /**
     * Create an instance of {@link Timer }
     * 
     */
    public Timer createTimer() {
        return new Timer();
    }

    /**
     * Create an instance of {@link TimeRange }
     * 
     */
    public TimeRange createTimeRange() {
        return new TimeRange();
    }

    /**
     * Create an instance of {@link DispositionsUpdateSettings }
     * 
     */
    public DispositionsUpdateSettings createDispositionsUpdateSettings() {
        return new DispositionsUpdateSettings();
    }

    /**
     * Create an instance of {@link KeyValuePair }
     * 
     */
    public KeyValuePair createKeyValuePair() {
        return new KeyValuePair();
    }

    /**
     * Create an instance of {@link ScriptParameterValue }
     * 
     */
    public ScriptParameterValue createScriptParameterValue() {
        return new ScriptParameterValue();
    }

    /**
     * Create an instance of {@link FieldEntry }
     * 
     */
    public FieldEntry createFieldEntry() {
        return new FieldEntry();
    }

    /**
     * Create an instance of {@link CallVariableRestriction }
     * 
     */
    public CallVariableRestriction createCallVariableRestriction() {
        return new CallVariableRestriction();
    }

    /**
     * Create an instance of {@link TtsInfo }
     * 
     */
    public TtsInfo createTtsInfo() {
        return new TtsInfo();
    }

    /**
     * Create an instance of {@link MiscVccOptions }
     * 
     */
    public MiscVccOptions createMiscVccOptions() {
        return new MiscVccOptions();
    }

    /**
     * Create an instance of {@link OutboundCampaign }
     * 
     */
    public OutboundCampaign createOutboundCampaign() {
        return new OutboundCampaign();
    }

    /**
     * Create an instance of {@link PasswordPolicyEntryValue }
     * 
     */
    public PasswordPolicyEntryValue createPasswordPolicyEntryValue() {
        return new PasswordPolicyEntryValue();
    }

    /**
     * Create an instance of {@link CampaignFilterCriterion }
     * 
     */
    public CampaignFilterCriterion createCampaignFilterCriterion() {
        return new CampaignFilterCriterion();
    }

    /**
     * Create an instance of {@link CrmDeleteSettings }
     * 
     */
    public CrmDeleteSettings createCrmDeleteSettings() {
        return new CrmDeleteSettings();
    }

    /**
     * Create an instance of {@link AgentRole }
     * 
     */
    public AgentRole createAgentRole() {
        return new AgentRole();
    }

    /**
     * Create an instance of {@link ReasonCode }
     * 
     */
    public ReasonCode createReasonCode() {
        return new ReasonCode();
    }

    /**
     * Create an instance of {@link WebConnector }
     * 
     */
    public WebConnector createWebConnector() {
        return new WebConnector();
    }

    /**
     * Create an instance of {@link UserInfo }
     * 
     */
    public UserInfo createUserInfo() {
        return new UserInfo();
    }

    /**
     * Create an instance of {@link ReportTimeCriteria }
     * 
     */
    public ReportTimeCriteria createReportTimeCriteria() {
        return new ReportTimeCriteria();
    }

    /**
     * Create an instance of {@link ContactFieldRestriction }
     * 
     */
    public ContactFieldRestriction createContactFieldRestriction() {
        return new ContactFieldRestriction();
    }

    /**
     * Create an instance of {@link DispositionTypeParams }
     * 
     */
    public DispositionTypeParams createDispositionTypeParams() {
        return new DispositionTypeParams();
    }

    /**
     * Create an instance of {@link ReportRowData }
     * 
     */
    public ReportRowData createReportRowData() {
        return new ReportRowData();
    }

    /**
     * Create an instance of {@link ReportObjectList }
     * 
     */
    public ReportObjectList createReportObjectList() {
        return new ReportObjectList();
    }

    /**
     * Create an instance of {@link CampaignProfileFilter }
     * 
     */
    public CampaignProfileFilter createCampaignProfileFilter() {
        return new CampaignProfileFilter();
    }

    /**
     * Create an instance of {@link Skill }
     * 
     */
    public Skill createSkill() {
        return new Skill();
    }

    /**
     * Create an instance of {@link SupervisorRole }
     * 
     */
    public SupervisorRole createSupervisorRole() {
        return new SupervisorRole();
    }

    /**
     * Create an instance of {@link AgentGroup }
     * 
     */
    public AgentGroup createAgentGroup() {
        return new AgentGroup();
    }

    /**
     * Create an instance of {@link CampaignNumberSchedule }
     * 
     */
    public CampaignNumberSchedule createCampaignNumberSchedule() {
        return new CampaignNumberSchedule();
    }

    /**
     * Create an instance of {@link OrderByField }
     * 
     */
    public OrderByField createOrderByField() {
        return new OrderByField();
    }

    /**
     * Create an instance of {@link ListDeleteSettings }
     * 
     */
    public ListDeleteSettings createListDeleteSettings() {
        return new ListDeleteSettings();
    }

    /**
     * Create an instance of {@link CampaignDialingSchedule }
     * 
     */
    public CampaignDialingSchedule createCampaignDialingSchedule() {
        return new CampaignDialingSchedule();
    }

    /**
     * Create an instance of {@link ImportData }
     * 
     */
    public ImportData createImportData() {
        return new ImportData();
    }

    /**
     * Create an instance of {@link IvrScriptSchedule }
     * 
     */
    public IvrScriptSchedule createIvrScriptSchedule() {
        return new IvrScriptSchedule();
    }

    /**
     * Create an instance of {@link EmailNotifications }
     * 
     */
    public EmailNotifications createEmailNotifications() {
        return new EmailNotifications();
    }

    /**
     * Create an instance of {@link BasicImportSettings }
     * 
     */
    public BasicImportSettings createBasicImportSettings() {
        return new BasicImportSettings();
    }

    /**
     * Create an instance of {@link KeyPerfomanceIndicators }
     * 
     */
    public KeyPerfomanceIndicators createKeyPerfomanceIndicators() {
        return new KeyPerfomanceIndicators();
    }

    /**
     * Create an instance of {@link UserProfile }
     * 
     */
    public UserProfile createUserProfile() {
        return new UserProfile();
    }

    /**
     * Create an instance of {@link ListUpdateSettings }
     * 
     */
    public ListUpdateSettings createListUpdateSettings() {
        return new ListUpdateSettings();
    }

    /**
     * Create an instance of {@link AdminPermission }
     * 
     */
    public AdminPermission createAdminPermission() {
        return new AdminPermission();
    }

    /**
     * Create an instance of {@link CrmCriteriaGrouping }
     * 
     */
    public CrmCriteriaGrouping createCrmCriteriaGrouping() {
        return new CrmCriteriaGrouping();
    }

    /**
     * Create an instance of {@link PasswordPolicies }
     * 
     */
    public PasswordPolicies createPasswordPolicies() {
        return new PasswordPolicies();
    }

    /**
     * Create an instance of {@link ContactField }
     * 
     */
    public ContactField createContactField() {
        return new ContactField();
    }

    /**
     * Create an instance of {@link CallVariable }
     * 
     */
    public CallVariable createCallVariable() {
        return new CallVariable();
    }

    /**
     * Create an instance of {@link IvrScriptDef }
     * 
     */
    public IvrScriptDef createIvrScriptDef() {
        return new IvrScriptDef();
    }

    /**
     * Create an instance of {@link InboundCampaign }
     * 
     */
    public InboundCampaign createInboundCampaign() {
        return new InboundCampaign();
    }

    /**
     * Create an instance of {@link ListImportResult }
     * 
     */
    public ListImportResult createListImportResult() {
        return new ListImportResult();
    }

    /**
     * Create an instance of {@link PromptInfo }
     * 
     */
    public PromptInfo createPromptInfo() {
        return new PromptInfo();
    }

    /**
     * Create an instance of {@link DispositionCount }
     * 
     */
    public DispositionCount createDispositionCount() {
        return new DispositionCount();
    }

    /**
     * Create an instance of {@link VccConfiguration }
     * 
     */
    public VccConfiguration createVccConfiguration() {
        return new VccConfiguration();
    }

    /**
     * Create an instance of {@link CrmUpdateSettings }
     * 
     */
    public CrmUpdateSettings createCrmUpdateSettings() {
        return new CrmUpdateSettings();
    }

    /**
     * Create an instance of {@link CustomReportCriteria }
     * 
     */
    public CustomReportCriteria createCustomReportCriteria() {
        return new CustomReportCriteria();
    }

    /**
     * Create an instance of {@link SkillInfo }
     * 
     */
    public SkillInfo createSkillInfo() {
        return new SkillInfo();
    }

    /**
     * Create an instance of {@link CrmImportResult }
     * 
     */
    public CrmImportResult createCrmImportResult() {
        return new CrmImportResult();
    }

    /**
     * Create an instance of {@link DispositionsImportResult }
     * 
     */
    public DispositionsImportResult createDispositionsImportResult() {
        return new DispositionsImportResult();
    }

    /**
     * Create an instance of {@link UserRoles }
     * 
     */
    public UserRoles createUserRoles() {
        return new UserRoles();
    }

    /**
     * Create an instance of {@link CallVariablesGroup }
     * 
     */
    public CallVariablesGroup createCallVariablesGroup() {
        return new CallVariablesGroup();
    }

    /**
     * Create an instance of {@link ReportingPermission }
     * 
     */
    public ReportingPermission createReportingPermission() {
        return new ReportingPermission();
    }

    /**
     * Create an instance of {@link AgentAuditReportCriteria }
     * 
     */
    public AgentAuditReportCriteria createAgentAuditReportCriteria() {
        return new AgentAuditReportCriteria();
    }

    /**
     * Create an instance of {@link ListInfo }
     * 
     */
    public ListInfo createListInfo() {
        return new ListInfo();
    }

    /**
     * Create an instance of {@link CampaignDialingAction }
     * 
     */
    public CampaignDialingAction createCampaignDialingAction() {
        return new CampaignDialingAction();
    }

    /**
     * Create an instance of {@link Disposition }
     * 
     */
    public Disposition createDisposition() {
        return new Disposition();
    }

    /**
     * Create an instance of {@link UserSkill }
     * 
     */
    public UserSkill createUserSkill() {
        return new UserSkill();
    }

    /**
     * Create an instance of {@link CampaignCallWrapup }
     * 
     */
    public CampaignCallWrapup createCampaignCallWrapup() {
        return new CampaignCallWrapup();
    }

    /**
     * Create an instance of {@link AdminRole }
     * 
     */
    public AdminRole createAdminRole() {
        return new AdminRole();
    }

    /**
     * Create an instance of {@link CrmFieldCriterion }
     * 
     */
    public CrmFieldCriterion createCrmFieldCriterion() {
        return new CrmFieldCriterion();
    }

    /**
     * Create an instance of {@link DialingRule }
     * 
     */
    public DialingRule createDialingRule() {
        return new DialingRule();
    }

    /**
     * Create an instance of {@link ListState }
     * 
     */
    public ListState createListState() {
        return new ListState();
    }

    /**
     * Create an instance of {@link FtpImportSettings }
     * 
     */
    public FtpImportSettings createFtpImportSettings() {
        return new FtpImportSettings();
    }

    /**
     * Create an instance of {@link Campaign }
     * 
     */
    public Campaign createCampaign() {
        return new Campaign();
    }

    /**
     * Create an instance of {@link AgentProductivity }
     * 
     */
    public AgentProductivity createAgentProductivity() {
        return new AgentProductivity();
    }

    /**
     * Create an instance of {@link CrmLookupCriteria }
     * 
     */
    public CrmLookupCriteria createCrmLookupCriteria() {
        return new CrmLookupCriteria();
    }

    /**
     * Create an instance of {@link DateRange }
     * 
     */
    public DateRange createDateRange() {
        return new DateRange();
    }

    /**
     * Create an instance of {@link CannedReport }
     * 
     */
    public CannedReport createCannedReport() {
        return new CannedReport();
    }

    /**
     * Create an instance of {@link AgentPermission }
     * 
     */
    public AgentPermission createAgentPermission() {
        return new AgentPermission();
    }

    /**
     * Create an instance of {@link SupervisorPermission }
     * 
     */
    public SupervisorPermission createSupervisorPermission() {
        return new SupervisorPermission();
    }

    /**
     * Create an instance of {@link UserGeneralInfo }
     * 
     */
    public UserGeneralInfo createUserGeneralInfo() {
        return new UserGeneralInfo();
    }

    /**
     * Create an instance of {@link CallLogReportCriteria }
     * 
     */
    public CallLogReportCriteria createCallLogReportCriteria() {
        return new CallLogReportCriteria();
    }

    /**
     * Create an instance of {@link GeneralCampaign }
     * 
     */
    public GeneralCampaign createGeneralCampaign() {
        return new GeneralCampaign();
    }

    /**
     * Create an instance of {@link ImportIdentifier }
     * 
     */
    public ImportIdentifier createImportIdentifier() {
        return new ImportIdentifier();
    }

    /**
     * Create an instance of {@link BasicImportResult.WarningsCount.Entry }
     * 
     */
    public BasicImportResult.WarningsCount.Entry createBasicImportResultWarningsCountEntry() {
        return new BasicImportResult.WarningsCount.Entry();
    }

    /**
     * Create an instance of {@link Record.Values }
     * 
     */
    public Record.Values createRecordValues() {
        return new Record.Values();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignDNISListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignDNISListResponse")
    public JAXBElement<GetCampaignDNISListResponse> createGetCampaignDNISListResponse(GetCampaignDNISListResponse value) {
        return new JAXBElement<GetCampaignDNISListResponse>(_GetCampaignDNISListResponse_QNAME, GetCampaignDNISListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserCannedReports }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserCannedReports")
    public JAXBElement<ModifyUserCannedReports> createModifyUserCannedReports(ModifyUserCannedReports value) {
        return new JAXBElement<ModifyUserCannedReports>(_ModifyUserCannedReports_QNAME, ModifyUserCannedReports.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SkillIsNotAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "SkillIsNotAssignedFault")
    public JAXBElement<SkillIsNotAssignedException> createSkillIsNotAssignedFault(SkillIsNotAssignedException value) {
        return new JAXBElement<SkillIsNotAssignedException>(_SkillIsNotAssignedFault_QNAME, SkillIsNotAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSkillRemoveResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "userSkillRemoveResponse")
    public JAXBElement<UserSkillRemoveResponse> createUserSkillRemoveResponse(UserSkillRemoveResponse value) {
        return new JAXBElement<UserSkillRemoveResponse>(_UserSkillRemoveResponse_QNAME, UserSkillRemoveResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDispositionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createDispositionResponse")
    public JAXBElement<CreateDispositionResponse> createCreateDispositionResponse(CreateDispositionResponse value) {
        return new JAXBElement<CreateDispositionResponse>(_CreateDispositionResponse_QNAME, CreateDispositionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileFilterOrderResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileFilterOrderResponse")
    public JAXBElement<ModifyCampaignProfileFilterOrderResponse> createModifyCampaignProfileFilterOrderResponse(ModifyCampaignProfileFilterOrderResponse value) {
        return new JAXBElement<ModifyCampaignProfileFilterOrderResponse>(_ModifyCampaignProfileFilterOrderResponse_QNAME, ModifyCampaignProfileFilterOrderResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDispositionsToCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addDispositionsToCampaign")
    public JAXBElement<AddDispositionsToCampaign> createAddDispositionsToCampaign(AddDispositionsToCampaign value) {
        return new JAXBElement<AddDispositionsToCampaign>(_AddDispositionsToCampaign_QNAME, AddDispositionsToCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignLists }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignLists")
    public JAXBElement<ModifyCampaignLists> createModifyCampaignLists(ModifyCampaignLists value) {
        return new JAXBElement<ModifyCampaignLists>(_ModifyCampaignLists_QNAME, ModifyCampaignLists.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReasonCodeByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReasonCodeByType")
    public JAXBElement<GetReasonCodeByType> createGetReasonCodeByType(GetReasonCodeByType value) {
        return new JAXBElement<GetReasonCodeByType>(_GetReasonCodeByType_QNAME, GetReasonCodeByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddListsToCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addListsToCampaignResponse")
    public JAXBElement<AddListsToCampaignResponse> createAddListsToCampaignResponse(AddListsToCampaignResponse value) {
        return new JAXBElement<AddListsToCampaignResponse>(_AddListsToCampaignResponse_QNAME, AddListsToCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCallVariablesGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createCallVariablesGroup")
    public JAXBElement<CreateCallVariablesGroup> createCreateCallVariablesGroup(CreateCallVariablesGroup value) {
        return new JAXBElement<CreateCallVariablesGroup>(_CreateCallVariablesGroup_QNAME, CreateCallVariablesGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncAddRecordsToListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "asyncAddRecordsToListResponse")
    public JAXBElement<AsyncAddRecordsToListResponse> createAsyncAddRecordsToListResponse(AsyncAddRecordsToListResponse value) {
        return new JAXBElement<AsyncAddRecordsToListResponse>(_AsyncAddRecordsToListResponse_QNAME, AsyncAddRecordsToListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSkillModify }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "userSkillModify")
    public JAXBElement<UserSkillModify> createUserSkillModify(UserSkillModify value) {
        return new JAXBElement<UserSkillModify>(_UserSkillModify_QNAME, UserSkillModify.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignProfileFilter }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignProfileFilter")
    public JAXBElement<GetCampaignProfileFilter> createGetCampaignProfileFilter(GetCampaignProfileFilter value) {
        return new JAXBElement<GetCampaignProfileFilter>(_GetCampaignProfileFilter_QNAME, GetCampaignProfileFilter.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContactField }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteContactField")
    public JAXBElement<DeleteContactField> createDeleteContactField(DeleteContactField value) {
        return new JAXBElement<DeleteContactField>(_DeleteContactField_QNAME, DeleteContactField.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportRecordsCountLimitExceededException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ImportSizeLimitExceededFault")
    public JAXBElement<ImportRecordsCountLimitExceededException> createImportSizeLimitExceededFault(ImportRecordsCountLimitExceededException value) {
        return new JAXBElement<ImportRecordsCountLimitExceededException>(_ImportSizeLimitExceededFault_QNAME, ImportRecordsCountLimitExceededException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromContactsFtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromContactsFtpResponse")
    public JAXBElement<DeleteFromContactsFtpResponse> createDeleteFromContactsFtpResponse(DeleteFromContactsFtpResponse value) {
        return new JAXBElement<DeleteFromContactsFtpResponse>(_DeleteFromContactsFtpResponse_QNAME, DeleteFromContactsFtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillInfo")
    public JAXBElement<GetSkillInfo> createGetSkillInfo(GetSkillInfo value) {
        return new JAXBElement<GetSkillInfo>(_GetSkillInfo_QNAME, GetSkillInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDefaultIVRScheduleResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setDefaultIVRScheduleResponse")
    public JAXBElement<SetDefaultIVRScheduleResponse> createSetDefaultIVRScheduleResponse(SetDefaultIVRScheduleResponse value) {
        return new JAXBElement<SetDefaultIVRScheduleResponse>(_SetDefaultIVRScheduleResponse_QNAME, SetDefaultIVRScheduleResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallVariableGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallVariableGroupsResponse")
    public JAXBElement<GetCallVariableGroupsResponse> createGetCallVariableGroupsResponse(GetCallVariableGroupsResponse value) {
        return new JAXBElement<GetCallVariableGroupsResponse>(_GetCallVariableGroupsResponse_QNAME, GetCallVariableGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidAccountException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "InvalidAccountFault")
    public JAXBElement<InvalidAccountException> createInvalidAccountFault(InvalidAccountException value) {
        return new JAXBElement<InvalidAccountException>(_InvalidAccountFault_QNAME, InvalidAccountException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignProfileDispositions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignProfileDispositions")
    public JAXBElement<GetCampaignProfileDispositions> createGetCampaignProfileDispositions(GetCampaignProfileDispositions value) {
        return new JAXBElement<GetCampaignProfileDispositions>(_GetCampaignProfileDispositions_QNAME, GetCampaignProfileDispositions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCallVariablesGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCallVariablesGroupResponse")
    public JAXBElement<DeleteCallVariablesGroupResponse> createDeleteCallVariablesGroupResponse(DeleteCallVariablesGroupResponse value) {
        return new JAXBElement<DeleteCallVariablesGroupResponse>(_DeleteCallVariablesGroupResponse_QNAME, DeleteCallVariablesGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUser")
    public JAXBElement<ModifyUser> createModifyUser(ModifyUser value) {
        return new JAXBElement<ModifyUser>(_ModifyUser_QNAME, ModifyUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DispositionAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DispositionAlreadyExistsFault")
    public JAXBElement<DispositionAlreadyExistsException> createDispositionAlreadyExistsFault(DispositionAlreadyExistsException value) {
        return new JAXBElement<DispositionAlreadyExistsException>(_DispositionAlreadyExistsFault_QNAME, DispositionAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSkillsToCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addSkillsToCampaign")
    public JAXBElement<AddSkillsToCampaign> createAddSkillsToCampaign(AddSkillsToCampaign value) {
        return new JAXBElement<AddSkillsToCampaign>(_AddSkillsToCampaign_QNAME, AddSkillsToCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPromptWavResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyPromptWavResponse")
    public JAXBElement<ModifyPromptWavResponse> createModifyPromptWavResponse(ModifyPromptWavResponse value) {
        return new JAXBElement<ModifyPromptWavResponse>(_ModifyPromptWavResponse_QNAME, ModifyPromptWavResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSkill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createSkill")
    public JAXBElement<CreateSkill> createCreateSkill(CreateSkill value) {
        return new JAXBElement<CreateSkill>(_CreateSkill_QNAME, CreateSkill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetListPosition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "resetListPosition")
    public JAXBElement<ResetListPosition> createResetListPosition(ResetListPosition value) {
        return new JAXBElement<ResetListPosition>(_ResetListPosition_QNAME, ResetListPosition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteWebConnector }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteWebConnector")
    public JAXBElement<DeleteWebConnector> createDeleteWebConnector(DeleteWebConnector value) {
        return new JAXBElement<DeleteWebConnector>(_DeleteWebConnector_QNAME, DeleteWebConnector.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsReportRunning }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "isReportRunning")
    public JAXBElement<IsReportRunning> createIsReportRunning(IsReportRunning value) {
        return new JAXBElement<IsReportRunning>(_IsReportRunning_QNAME, IsReportRunning.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteReasonCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteReasonCodeResponse")
    public JAXBElement<DeleteReasonCodeResponse> createDeleteReasonCodeResponse(DeleteReasonCodeResponse value) {
        return new JAXBElement<DeleteReasonCodeResponse>(_DeleteReasonCodeResponse_QNAME, DeleteReasonCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromListCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromListCsvResponse")
    public JAXBElement<DeleteFromListCsvResponse> createDeleteFromListCsvResponse(DeleteFromListCsvResponse value) {
        return new JAXBElement<DeleteFromListCsvResponse>(_DeleteFromListCsvResponse_QNAME, DeleteFromListCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "runReport")
    public JAXBElement<RunReport> createRunReport(RunReport value) {
        return new JAXBElement<RunReport>(_RunReport_QNAME, RunReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServiceUnavailableException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ServiceUnavailableFault")
    public JAXBElement<ServiceUnavailableException> createServiceUnavailableFault(ServiceUnavailableException value) {
        return new JAXBElement<ServiceUnavailableException>(_ServiceUnavailableFault_QNAME, ServiceUnavailableException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteContactFieldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteContactFieldResponse")
    public JAXBElement<DeleteContactFieldResponse> createDeleteContactFieldResponse(DeleteContactFieldResponse value) {
        return new JAXBElement<DeleteContactFieldResponse>(_DeleteContactFieldResponse_QNAME, DeleteContactFieldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAutodialCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createAutodialCampaign")
    public JAXBElement<CreateAutodialCampaign> createCreateAutodialCampaign(CreateAutodialCampaign value) {
        return new JAXBElement<CreateAutodialCampaign>(_CreateAutodialCampaign_QNAME, CreateAutodialCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "resetCampaign")
    public JAXBElement<ResetCampaign> createResetCampaign(ResetCampaign value) {
        return new JAXBElement<ResetCampaign>(_ResetCampaign_QNAME, ResetCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignProfileFilterResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignProfileFilterResponse")
    public JAXBElement<GetCampaignProfileFilterResponse> createGetCampaignProfileFilterResponse(GetCampaignProfileFilterResponse value) {
        return new JAXBElement<GetCampaignProfileFilterResponse>(_GetCampaignProfileFilterResponse_QNAME, GetCampaignProfileFilterResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactFieldsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getContactFieldsResponse")
    public JAXBElement<GetContactFieldsResponse> createGetContactFieldsResponse(GetContactFieldsResponse value) {
        return new JAXBElement<GetContactFieldsResponse>(_GetContactFieldsResponse_QNAME, GetContactFieldsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifySkill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifySkill")
    public JAXBElement<ModifySkill> createModifySkill(ModifySkill value) {
        return new JAXBElement<ModifySkill>(_ModifySkill_QNAME, ModifySkill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCrmRecordResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateCrmRecordResponse")
    public JAXBElement<UpdateCrmRecordResponse> createUpdateCrmRecordResponse(UpdateCrmRecordResponse value) {
        return new JAXBElement<UpdateCrmRecordResponse>(_UpdateCrmRecordResponse_QNAME, UpdateCrmRecordResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateIVRScriptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createIVRScriptResponse")
    public JAXBElement<CreateIVRScriptResponse> createCreateIVRScriptResponse(CreateIVRScriptResponse value) {
        return new JAXBElement<CreateIVRScriptResponse>(_CreateIVRScriptResponse_QNAME, CreateIVRScriptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAutodialCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyAutodialCampaign")
    public JAXBElement<ModifyAutodialCampaign> createModifyAutodialCampaign(ModifyAutodialCampaign value) {
        return new JAXBElement<ModifyAutodialCampaign>(_ModifyAutodialCampaign_QNAME, ModifyAutodialCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ListNotFoundFault")
    public JAXBElement<ListNotFoundException> createListNotFoundFault(ListNotFoundException value) {
        return new JAXBElement<ListNotFoundException>(_ListNotFoundFault_QNAME, ListNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserProfile")
    public JAXBElement<GetUserProfile> createGetUserProfile(GetUserProfile value) {
        return new JAXBElement<GetUserProfile>(_GetUserProfile_QNAME, GetUserProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactRecordsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getContactRecordsResponse")
    public JAXBElement<GetContactRecordsResponse> createGetContactRecordsResponse(GetContactRecordsResponse value) {
        return new JAXBElement<GetContactRecordsResponse>(_GetContactRecordsResponse_QNAME, GetContactRecordsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDispositionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateDispositionsResponse")
    public JAXBElement<UpdateDispositionsResponse> createUpdateDispositionsResponse(UpdateDispositionsResponse value) {
        return new JAXBElement<UpdateDispositionsResponse>(_UpdateDispositionsResponse_QNAME, UpdateDispositionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDialingRules }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setDialingRules")
    public JAXBElement<SetDialingRules> createSetDialingRules(SetDialingRules value) {
        return new JAXBElement<SetDialingRules>(_SetDialingRules_QNAME, SetDialingRules.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyContactFieldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyContactFieldResponse")
    public JAXBElement<ModifyContactFieldResponse> createModifyContactFieldResponse(ModifyContactFieldResponse value) {
        return new JAXBElement<ModifyContactFieldResponse>(_ModifyContactFieldResponse_QNAME, ModifyContactFieldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FinderException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "FinderException")
    public JAXBElement<FinderException> createFinderException(FinderException value) {
        return new JAXBElement<FinderException>(_FinderException_QNAME, FinderException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CampaignNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "CampaignNotFoundFault")
    public JAXBElement<CampaignNotFoundException> createCampaignNotFoundFault(CampaignNotFoundException value) {
        return new JAXBElement<CampaignNotFoundException>(_CampaignNotFoundFault_QNAME, CampaignNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserHasNoRequiredRolesException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserHasNoRequiredRoleFault")
    public JAXBElement<UserHasNoRequiredRolesException> createUserHasNoRequiredRoleFault(UserHasNoRequiredRolesException value) {
        return new JAXBElement<UserHasNoRequiredRolesException>(_UserHasNoRequiredRoleFault_QNAME, UserHasNoRequiredRolesException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPromptWavInline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyPromptWavInline")
    public JAXBElement<ModifyPromptWavInline> createModifyPromptWavInline(ModifyPromptWavInline value) {
        return new JAXBElement<ModifyPromptWavInline>(_ModifyPromptWavInline_QNAME, ModifyPromptWavInline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ParseException")
    public JAXBElement<ParseException> createParseException(ParseException value) {
        return new JAXBElement<ParseException>(_ParseException_QNAME, ParseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromContactsCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromContactsCsvResponse")
    public JAXBElement<DeleteFromContactsCsvResponse> createDeleteFromContactsCsvResponse(DeleteFromContactsCsvResponse value) {
        return new JAXBElement<DeleteFromContactsCsvResponse>(_DeleteFromContactsCsvResponse_QNAME, DeleteFromContactsCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSkillAdd }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "userSkillAdd")
    public JAXBElement<UserSkillAdd> createUserSkillAdd(UserSkillAdd value) {
        return new JAXBElement<UserSkillAdd>(_UserSkillAdd_QNAME, UserSkillAdd.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportCancelledException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ImportCancelledFault")
    public JAXBElement<ImportCancelledException> createImportCancelledFault(ImportCancelledException value) {
        return new JAXBElement<ImportCancelledException>(_ImportCancelledFault_QNAME, ImportCancelledException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPromptWavInlineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addPromptWavInlineResponse")
    public JAXBElement<AddPromptWavInlineResponse> createAddPromptWavInlineResponse(AddPromptWavInlineResponse value) {
        return new JAXBElement<AddPromptWavInlineResponse>(_AddPromptWavInlineResponse_QNAME, AddPromptWavInlineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRecordFromListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteRecordFromListResponse")
    public JAXBElement<DeleteRecordFromListResponse> createDeleteRecordFromListResponse(DeleteRecordFromListResponse value) {
        return new JAXBElement<DeleteRecordFromListResponse>(_DeleteRecordFromListResponse_QNAME, DeleteRecordFromListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCrmImportResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCrmImportResultResponse")
    public JAXBElement<GetCrmImportResultResponse> createGetCrmImportResultResponse(GetCrmImportResultResponse value) {
        return new JAXBElement<GetCrmImportResultResponse>(_GetCrmImportResultResponse_QNAME, GetCrmImportResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillsInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillsInfoResponse")
    public JAXBElement<GetSkillsInfoResponse> createGetSkillsInfoResponse(GetSkillsInfoResponse value) {
        return new JAXBElement<GetSkillsInfoResponse>(_GetSkillsInfoResponse_QNAME, GetSkillsInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "resetCampaignResponse")
    public JAXBElement<ResetCampaignResponse> createResetCampaignResponse(ResetCampaignResponse value) {
        return new JAXBElement<ResetCampaignResponse>(_ResetCampaignResponse_QNAME, ResetCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillResponse")
    public JAXBElement<GetSkillResponse> createGetSkillResponse(GetSkillResponse value) {
        return new JAXBElement<GetSkillResponse>(_GetSkillResponse_QNAME, GetSkillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSkillRemove }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "userSkillRemove")
    public JAXBElement<UserSkillRemove> createUserSkillRemove(UserSkillRemove value) {
        return new JAXBElement<UserSkillRemove>(_UserSkillRemove_QNAME, UserSkillRemove.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPromptWavInlineResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyPromptWavInlineResponse")
    public JAXBElement<ModifyPromptWavInlineResponse> createModifyPromptWavInlineResponse(ModifyPromptWavInlineResponse value) {
        return new JAXBElement<ModifyPromptWavInlineResponse>(_ModifyPromptWavInlineResponse_QNAME, ModifyPromptWavInlineResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ObjectNotFoundFault")
    public JAXBElement<ObjectNotFoundException> createObjectNotFoundFault(ObjectNotFoundException value) {
        return new JAXBElement<ObjectNotFoundException>(_ObjectNotFoundFault_QNAME, ObjectNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallLogReportCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallLogReportCsvResponse")
    public JAXBElement<GetCallLogReportCsvResponse> createGetCallLogReportCsvResponse(GetCallLogReportCsvResponse value) {
        return new JAXBElement<GetCallLogReportCsvResponse>(_GetCallLogReportCsvResponse_QNAME, GetCallLogReportCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAgentGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createAgentGroup")
    public JAXBElement<CreateAgentGroup> createCreateAgentGroup(CreateAgentGroup value) {
        return new JAXBElement<CreateAgentGroup>(_CreateAgentGroup_QNAME, CreateAgentGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAutodialCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAutodialCampaignResponse")
    public JAXBElement<GetAutodialCampaignResponse> createGetAutodialCampaignResponse(GetAutodialCampaignResponse value) {
        return new JAXBElement<GetAutodialCampaignResponse>(_GetAutodialCampaignResponse_QNAME, GetAutodialCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPromptTTS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyPromptTTS")
    public JAXBElement<ModifyPromptTTS> createModifyPromptTTS(ModifyPromptTTS value) {
        return new JAXBElement<ModifyPromptTTS>(_ModifyPromptTTS_QNAME, ModifyPromptTTS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDispositions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateDispositions")
    public JAXBElement<UpdateDispositions> createUpdateDispositions(UpdateDispositions value) {
        return new JAXBElement<UpdateDispositions>(_UpdateDispositions_QNAME, UpdateDispositions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IvrScriptNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "IvrScriptNotFoundFault")
    public JAXBElement<IvrScriptNotFoundException> createIvrScriptNotFoundFault(IvrScriptNotFoundException value) {
        return new JAXBElement<IvrScriptNotFoundException>(_IvrScriptNotFoundFault_QNAME, IvrScriptNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromContactsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromContactsResponse")
    public JAXBElement<DeleteFromContactsResponse> createDeleteFromContactsResponse(DeleteFromContactsResponse value) {
        return new JAXBElement<DeleteFromContactsResponse>(_DeleteFromContactsResponse_QNAME, DeleteFromContactsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectsCountLimitExceededException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ObjectsCountLimitExceededFault")
    public JAXBElement<ObjectsCountLimitExceededException> createObjectsCountLimitExceededFault(ObjectsCountLimitExceededException value) {
        return new JAXBElement<ObjectsCountLimitExceededException>(_ObjectsCountLimitExceededFault_QNAME, ObjectsCountLimitExceededException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDispositionsImportResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDispositionsImportResult")
    public JAXBElement<GetDispositionsImportResult> createGetDispositionsImportResult(GetDispositionsImportResult value) {
        return new JAXBElement<GetDispositionsImportResult>(_GetDispositionsImportResult_QNAME, GetDispositionsImportResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReasonCodeByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReasonCodeByTypeResponse")
    public JAXBElement<GetReasonCodeByTypeResponse> createGetReasonCodeByTypeResponse(GetReasonCodeByTypeResponse value) {
        return new JAXBElement<GetReasonCodeByTypeResponse>(_GetReasonCodeByTypeResponse_QNAME, GetReasonCodeByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteList")
    public JAXBElement<DeleteList> createDeleteList(DeleteList value) {
        return new JAXBElement<DeleteList>(_DeleteList_QNAME, DeleteList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContactFieldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createContactFieldResponse")
    public JAXBElement<CreateContactFieldResponse> createCreateContactFieldResponse(CreateContactFieldResponse value) {
        return new JAXBElement<CreateContactFieldResponse>(_CreateContactFieldResponse_QNAME, CreateContactFieldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddListsToCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addListsToCampaign")
    public JAXBElement<AddListsToCampaign> createAddListsToCampaign(AddListsToCampaign value) {
        return new JAXBElement<AddListsToCampaign>(_AddListsToCampaign_QNAME, AddListsToCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOutboundCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createOutboundCampaign")
    public JAXBElement<CreateOutboundCampaign> createCreateOutboundCampaign(CreateOutboundCampaign value) {
        return new JAXBElement<CreateOutboundCampaign>(_CreateOutboundCampaign_QNAME, CreateOutboundCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserGeneralInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserGeneralInfo")
    public JAXBElement<GetUserGeneralInfo> createGetUserGeneralInfo(GetUserGeneralInfo value) {
        return new JAXBElement<GetUserGeneralInfo>(_GetUserGeneralInfo_QNAME, GetUserGeneralInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PromptAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "PromptAlreadyExistsFault")
    public JAXBElement<PromptAlreadyExistsException> createPromptAlreadyExistsFault(PromptAlreadyExistsException value) {
        return new JAXBElement<PromptAlreadyExistsException>(_PromptAlreadyExistsFault_QNAME, PromptAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallLogReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallLogReportResponse")
    public JAXBElement<GetCallLogReportResponse> createGetCallLogReportResponse(GetCallLogReportResponse value) {
        return new JAXBElement<GetCallLogReportResponse>(_GetCallLogReportResponse_QNAME, GetCallLogReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillsResponse")
    public JAXBElement<GetSkillsResponse> createGetSkillsResponse(GetSkillsResponse value) {
        return new JAXBElement<GetSkillsResponse>(_GetSkillsResponse_QNAME, GetSkillsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSkillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createSkillResponse")
    public JAXBElement<CreateSkillResponse> createCreateSkillResponse(CreateSkillResponse value) {
        return new JAXBElement<CreateSkillResponse>(_CreateSkillResponse_QNAME, CreateSkillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidRegexpPatternException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "InvalidRegexpPatternFault")
    public JAXBElement<InvalidRegexpPatternException> createInvalidRegexpPatternFault(InvalidRegexpPatternException value) {
        return new JAXBElement<InvalidRegexpPatternException>(_InvalidRegexpPatternFault_QNAME, InvalidRegexpPatternException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DispositionAlreadyAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DispositionIsAlreadyAssignedFault")
    public JAXBElement<DispositionAlreadyAssignedException> createDispositionIsAlreadyAssignedFault(DispositionAlreadyAssignedException value) {
        return new JAXBElement<DispositionAlreadyAssignedException>(_DispositionIsAlreadyAssignedFault_QNAME, DispositionAlreadyAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyIVRScript }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyIVRScript")
    public JAXBElement<ModifyIVRScript> createModifyIVRScript(ModifyIVRScript value) {
        return new JAXBElement<ModifyIVRScript>(_ModifyIVRScript_QNAME, ModifyIVRScript.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveSkillsFromCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeSkillsFromCampaign")
    public JAXBElement<RemoveSkillsFromCampaign> createRemoveSkillsFromCampaign(RemoveSkillsFromCampaign value) {
        return new JAXBElement<RemoveSkillsFromCampaign>(_RemoveSkillsFromCampaign_QNAME, RemoveSkillsFromCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ObjectAlreadyExistsFault")
    public JAXBElement<ObjectAlreadyExistsException> createObjectAlreadyExistsFault(ObjectAlreadyExistsException value) {
        return new JAXBElement<ObjectAlreadyExistsException>(_ObjectAlreadyExistsFault_QNAME, ObjectAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteIVRScriptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteIVRScriptResponse")
    public JAXBElement<DeleteIVRScriptResponse> createDeleteIVRScriptResponse(DeleteIVRScriptResponse value) {
        return new JAXBElement<DeleteIVRScriptResponse>(_DeleteIVRScriptResponse_QNAME, DeleteIVRScriptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDispositionsFtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateDispositionsFtpResponse")
    public JAXBElement<UpdateDispositionsFtpResponse> createUpdateDispositionsFtpResponse(UpdateDispositionsFtpResponse value) {
        return new JAXBElement<UpdateDispositionsFtpResponse>(_UpdateDispositionsFtpResponse_QNAME, UpdateDispositionsFtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallVariables }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallVariables")
    public JAXBElement<GetCallVariables> createGetCallVariables(GetCallVariables value) {
        return new JAXBElement<GetCallVariables>(_GetCallVariables_QNAME, GetCallVariables.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignsResponse")
    public JAXBElement<GetCampaignsResponse> createGetCampaignsResponse(GetCampaignsResponse value) {
        return new JAXBElement<GetCampaignsResponse>(_GetCampaignsResponse_QNAME, GetCampaignsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SkillAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "SkillAlreadyExistsFault")
    public JAXBElement<SkillAlreadyExistsException> createSkillAlreadyExistsFault(SkillAlreadyExistsException value) {
        return new JAXBElement<SkillAlreadyExistsException>(_SkillAlreadyExistsFault_QNAME, SkillAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDNISFromCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeDNISFromCampaignResponse")
    public JAXBElement<RemoveDNISFromCampaignResponse> createRemoveDNISFromCampaignResponse(RemoveDNISFromCampaignResponse value) {
        return new JAXBElement<RemoveDNISFromCampaignResponse>(_RemoveDNISFromCampaignResponse_QNAME, RemoveDNISFromCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignListsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignListsResponse")
    public JAXBElement<ModifyCampaignListsResponse> createModifyCampaignListsResponse(ModifyCampaignListsResponse value) {
        return new JAXBElement<ModifyCampaignListsResponse>(_ModifyCampaignListsResponse_QNAME, ModifyCampaignListsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReasonCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReasonCodeResponse")
    public JAXBElement<GetReasonCodeResponse> createGetReasonCodeResponse(GetReasonCodeResponse value) {
        return new JAXBElement<GetReasonCodeResponse>(_GetReasonCodeResponse_QNAME, GetReasonCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createUserProfileResponse")
    public JAXBElement<CreateUserProfileResponse> createCreateUserProfileResponse(CreateUserProfileResponse value) {
        return new JAXBElement<CreateUserProfileResponse>(_CreateUserProfileResponse_QNAME, CreateUserProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAutodialCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyAutodialCampaignResponse")
    public JAXBElement<ModifyAutodialCampaignResponse> createModifyAutodialCampaignResponse(ModifyAutodialCampaignResponse value) {
        return new JAXBElement<ModifyAutodialCampaignResponse>(_ModifyAutodialCampaignResponse_QNAME, ModifyAutodialCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromContacts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromContacts")
    public JAXBElement<DeleteFromContacts> createDeleteFromContacts(DeleteFromContacts value) {
        return new JAXBElement<DeleteFromContacts>(_DeleteFromContacts_QNAME, DeleteFromContacts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateCrmRecord }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateCrmRecord")
    public JAXBElement<UpdateCrmRecord> createUpdateCrmRecord(UpdateCrmRecord value) {
        return new JAXBElement<UpdateCrmRecord>(_UpdateCrmRecord_QNAME, UpdateCrmRecord.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createUserProfile")
    public JAXBElement<CreateUserProfile> createCreateUserProfile(CreateUserProfile value) {
        return new JAXBElement<CreateUserProfile>(_CreateUserProfile_QNAME, CreateUserProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForceStopCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "forceStopCampaignResponse")
    public JAXBElement<ForceStopCampaignResponse> createForceStopCampaignResponse(ForceStopCampaignResponse value) {
        return new JAXBElement<ForceStopCampaignResponse>(_ForceStopCampaignResponse_QNAME, ForceStopCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidImportDataException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "InvalidImportDataFault")
    public JAXBElement<InvalidImportDataException> createInvalidImportDataFault(InvalidImportDataException value) {
        return new JAXBElement<InvalidImportDataException>(_InvalidImportDataFault_QNAME, InvalidImportDataException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissedOsLoginException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "MissedOsLoginFault")
    public JAXBElement<MissedOsLoginException> createMissedOsLoginFault(MissedOsLoginException value) {
        return new JAXBElement<MissedOsLoginException>(_MissedOsLoginFault_QNAME, MissedOsLoginException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserResponse")
    public JAXBElement<ModifyUserResponse> createModifyUserResponse(ModifyUserResponse value) {
        return new JAXBElement<ModifyUserResponse>(_ModifyUserResponse_QNAME, ModifyUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameDispositionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "renameDispositionResponse")
    public JAXBElement<RenameDispositionResponse> createRenameDispositionResponse(RenameDispositionResponse value) {
        return new JAXBElement<RenameDispositionResponse>(_RenameDispositionResponse_QNAME, RenameDispositionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAgentGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createAgentGroupResponse")
    public JAXBElement<CreateAgentGroupResponse> createCreateAgentGroupResponse(CreateAgentGroupResponse value) {
        return new JAXBElement<CreateAgentGroupResponse>(_CreateAgentGroupResponse_QNAME, CreateAgentGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentGroup")
    public JAXBElement<GetAgentGroup> createGetAgentGroup(GetAgentGroup value) {
        return new JAXBElement<GetAgentGroup>(_GetAgentGroup_QNAME, GetAgentGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileResponse")
    public JAXBElement<ModifyCampaignProfileResponse> createModifyCampaignProfileResponse(ModifyCampaignProfileResponse value) {
        return new JAXBElement<ModifyCampaignProfileResponse>(_ModifyCampaignProfileResponse_QNAME, ModifyCampaignProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteUserResponse")
    public JAXBElement<DeleteUserResponse> createDeleteUserResponse(DeleteUserResponse value) {
        return new JAXBElement<DeleteUserResponse>(_DeleteUserResponse_QNAME, DeleteUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsImportRunningResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "isImportRunningResponse")
    public JAXBElement<IsImportRunningResponse> createIsImportRunningResponse(IsImportRunningResponse value) {
        return new JAXBElement<IsImportRunningResponse>(_IsImportRunningResponse_QNAME, IsImportRunningResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCampaignResponse")
    public JAXBElement<DeleteCampaignResponse> createDeleteCampaignResponse(DeleteCampaignResponse value) {
        return new JAXBElement<DeleteCampaignResponse>(_DeleteCampaignResponse_QNAME, DeleteCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIVRScriptsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getIVRScriptsResponse")
    public JAXBElement<GetIVRScriptsResponse> createGetIVRScriptsResponse(GetIVRScriptsResponse value) {
        return new JAXBElement<GetIVRScriptsResponse>(_GetIVRScriptsResponse_QNAME, GetIVRScriptsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSkillAddResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "userSkillAddResponse")
    public JAXBElement<UserSkillAddResponse> createUserSkillAddResponse(UserSkillAddResponse value) {
        return new JAXBElement<UserSkillAddResponse>(_UserSkillAddResponse_QNAME, UserSkillAddResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCrmImportResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCrmImportResult")
    public JAXBElement<GetCrmImportResult> createGetCrmImportResult(GetCrmImportResult value) {
        return new JAXBElement<GetCrmImportResult>(_GetCrmImportResult_QNAME, GetCrmImportResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createListResponse")
    public JAXBElement<CreateListResponse> createCreateListResponse(CreateListResponse value) {
        return new JAXBElement<CreateListResponse>(_CreateListResponse_QNAME, CreateListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactsFtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateContactsFtp")
    public JAXBElement<UpdateContactsFtp> createUpdateContactsFtp(UpdateContactsFtp value) {
        return new JAXBElement<UpdateContactsFtp>(_UpdateContactsFtp_QNAME, UpdateContactsFtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createList")
    public JAXBElement<CreateList> createCreateList(CreateList value) {
        return new JAXBElement<CreateList>(_CreateList_QNAME, CreateList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCampaignProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCampaignProfile")
    public JAXBElement<DeleteCampaignProfile> createDeleteCampaignProfile(DeleteCampaignProfile value) {
        return new JAXBElement<DeleteCampaignProfile>(_DeleteCampaignProfile_QNAME, DeleteCampaignProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserProfileUserList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserProfileUserList")
    public JAXBElement<ModifyUserProfileUserList> createModifyUserProfileUserList(ModifyUserProfileUserList value) {
        return new JAXBElement<ModifyUserProfileUserList>(_ModifyUserProfileUserList_QNAME, ModifyUserProfileUserList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRecordToList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addRecordToList")
    public JAXBElement<AddRecordToList> createAddRecordToList(AddRecordToList value) {
        return new JAXBElement<AddRecordToList>(_AddRecordToList_QNAME, AddRecordToList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserProfilesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserProfilesResponse")
    public JAXBElement<GetUserProfilesResponse> createGetUserProfilesResponse(GetUserProfilesResponse value) {
        return new JAXBElement<GetUserProfilesResponse>(_GetUserProfilesResponse_QNAME, GetUserProfilesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCallVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCallVariableResponse")
    public JAXBElement<ModifyCallVariableResponse> createModifyCallVariableResponse(ModifyCallVariableResponse value) {
        return new JAXBElement<ModifyCallVariableResponse>(_ModifyCallVariableResponse_QNAME, ModifyCallVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDefaultIVRSchedule }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setDefaultIVRSchedule")
    public JAXBElement<SetDefaultIVRSchedule> createSetDefaultIVRSchedule(SetDefaultIVRSchedule value) {
        return new JAXBElement<SetDefaultIVRSchedule>(_SetDefaultIVRSchedule_QNAME, SetDefaultIVRSchedule.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DNISNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DNISNotFoundFault")
    public JAXBElement<DNISNotFoundException> createDNISNotFoundFault(DNISNotFoundException value) {
        return new JAXBElement<DNISNotFoundException>(_DNISNotFoundFault_QNAME, DNISNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IncorrectArgumentException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "IncorrectArgumentFault")
    public JAXBElement<IncorrectArgumentException> createIncorrectArgumentFault(IncorrectArgumentException value) {
        return new JAXBElement<IncorrectArgumentException>(_IncorrectArgumentFault_QNAME, IncorrectArgumentException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileDispositionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileDispositionsResponse")
    public JAXBElement<ModifyCampaignProfileDispositionsResponse> createModifyCampaignProfileDispositionsResponse(ModifyCampaignProfileDispositionsResponse value) {
        return new JAXBElement<ModifyCampaignProfileDispositionsResponse>(_ModifyCampaignProfileDispositionsResponse_QNAME, ModifyCampaignProfileDispositionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDNISList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDNISList")
    public JAXBElement<GetDNISList> createGetDNISList(GetDNISList value) {
        return new JAXBElement<GetDNISList>(_GetDNISList_QNAME, GetDNISList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactRecords }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getContactRecords")
    public JAXBElement<GetContactRecords> createGetContactRecords(GetContactRecords value) {
        return new JAXBElement<GetContactRecords>(_GetContactRecords_QNAME, GetContactRecords.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncAddRecordsToList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "asyncAddRecordsToList")
    public JAXBElement<AsyncAddRecordsToList> createAsyncAddRecordsToList(AsyncAddRecordsToList value) {
        return new JAXBElement<AsyncAddRecordsToList>(_AsyncAddRecordsToList_QNAME, AsyncAddRecordsToList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteRecordFromList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteRecordFromList")
    public JAXBElement<DeleteRecordFromList> createDeleteRecordFromList(DeleteRecordFromList value) {
        return new JAXBElement<DeleteRecordFromList>(_DeleteRecordFromList_QNAME, DeleteRecordFromList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ServerException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ServerFault")
    public JAXBElement<ServerException> createServerFault(ServerException value) {
        return new JAXBElement<ServerException>(_ServerFault_QNAME, ServerException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDispositionsToCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addDispositionsToCampaignResponse")
    public JAXBElement<AddDispositionsToCampaignResponse> createAddDispositionsToCampaignResponse(AddDispositionsToCampaignResponse value) {
        return new JAXBElement<AddDispositionsToCampaignResponse>(_AddDispositionsToCampaignResponse_QNAME, AddDispositionsToCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContactsLookupResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "contactsLookupResult")
    public JAXBElement<ContactsLookupResult> createContactsLookupResult(ContactsLookupResult value) {
        return new JAXBElement<ContactsLookupResult>(_ContactsLookupResult_QNAME, ContactsLookupResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfile")
    public JAXBElement<ModifyCampaignProfile> createModifyCampaignProfile(ModifyCampaignProfile value) {
        return new JAXBElement<ModifyCampaignProfile>(_ModifyCampaignProfile_QNAME, ModifyCampaignProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WavFileUploadFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "WavFileUploadFailedFault")
    public JAXBElement<WavFileUploadFailedException> createWavFileUploadFailedFault(WavFileUploadFailedException value) {
        return new JAXBElement<WavFileUploadFailedException>(_WavFileUploadFailedFault_QNAME, WavFileUploadFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyDisposition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyDisposition")
    public JAXBElement<ModifyDisposition> createModifyDisposition(ModifyDisposition value) {
        return new JAXBElement<ModifyDisposition>(_ModifyDisposition_QNAME, ModifyDisposition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAgentGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteAgentGroup")
    public JAXBElement<DeleteAgentGroup> createDeleteAgentGroup(DeleteAgentGroup value) {
        return new JAXBElement<DeleteAgentGroup>(_DeleteAgentGroup_QNAME, DeleteAgentGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateContactsResponse")
    public JAXBElement<UpdateContactsResponse> createUpdateContactsResponse(UpdateContactsResponse value) {
        return new JAXBElement<UpdateContactsResponse>(_UpdateContactsResponse_QNAME, UpdateContactsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateWebConnectorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createWebConnectorResponse")
    public JAXBElement<CreateWebConnectorResponse> createCreateWebConnectorResponse(CreateWebConnectorResponse value) {
        return new JAXBElement<CreateWebConnectorResponse>(_CreateWebConnectorResponse_QNAME, CreateWebConnectorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteWebConnectorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteWebConnectorResponse")
    public JAXBElement<DeleteWebConnectorResponse> createDeleteWebConnectorResponse(DeleteWebConnectorResponse value) {
        return new JAXBElement<DeleteWebConnectorResponse>(_DeleteWebConnectorResponse_QNAME, DeleteWebConnectorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDNISListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDNISListResponse")
    public JAXBElement<GetDNISListResponse> createGetDNISListResponse(GetDNISListResponse value) {
        return new JAXBElement<GetDNISListResponse>(_GetDNISListResponse_QNAME, GetDNISListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignStateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignStateResponse")
    public JAXBElement<GetCampaignStateResponse> createGetCampaignStateResponse(GetCampaignStateResponse value) {
        return new JAXBElement<GetCampaignStateResponse>(_GetCampaignStateResponse_QNAME, GetCampaignStateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallVariableGroups }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallVariableGroups")
    public JAXBElement<GetCallVariableGroups> createGetCallVariableGroups(GetCallVariableGroups value) {
        return new JAXBElement<GetCallVariableGroups>(_GetCallVariableGroups_QNAME, GetCallVariableGroups.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddRecordToListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addRecordToListResponse")
    public JAXBElement<AddRecordToListResponse> createAddRecordToListResponse(AddRecordToListResponse value) {
        return new JAXBElement<AddRecordToListResponse>(_AddRecordToListResponse_QNAME, AddRecordToListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidDateRangeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "InvalidDateRangeFault")
    public JAXBElement<InvalidDateRangeException> createInvalidDateRangeFault(InvalidDateRangeException value) {
        return new JAXBElement<InvalidDateRangeException>(_InvalidDateRangeFault_QNAME, InvalidDateRangeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SkillNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "SkillNotFoundFault")
    public JAXBElement<SkillNotFoundException> createSkillNotFoundFault(SkillNotFoundException value) {
        return new JAXBElement<SkillNotFoundException>(_SkillNotFoundFault_QNAME, SkillNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDialingRules }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDialingRules")
    public JAXBElement<GetDialingRules> createGetDialingRules(GetDialingRules value) {
        return new JAXBElement<GetDialingRules>(_GetDialingRules_QNAME, GetDialingRules.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveNumbersFromDncResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeNumbersFromDncResponse")
    public JAXBElement<RemoveNumbersFromDncResponse> createRemoveNumbersFromDncResponse(RemoveNumbersFromDncResponse value) {
        return new JAXBElement<RemoveNumbersFromDncResponse>(_RemoveNumbersFromDncResponse_QNAME, RemoveNumbersFromDncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentAuditReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentAuditReportResponse")
    public JAXBElement<GetAgentAuditReportResponse> createGetAgentAuditReportResponse(GetAgentAuditReportResponse value) {
        return new JAXBElement<GetAgentAuditReportResponse>(_GetAgentAuditReportResponse_QNAME, GetAgentAuditReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAgentGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyAgentGroup")
    public JAXBElement<ModifyAgentGroup> createModifyAgentGroup(ModifyAgentGroup value) {
        return new JAXBElement<ModifyAgentGroup>(_ModifyAgentGroup_QNAME, ModifyAgentGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListImportResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getListImportResultResponse")
    public JAXBElement<GetListImportResultResponse> createGetListImportResultResponse(GetListImportResultResponse value) {
        return new JAXBElement<GetListImportResultResponse>(_GetListImportResultResponse_QNAME, GetListImportResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserCannedReportsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserCannedReportsResponse")
    public JAXBElement<ModifyUserCannedReportsResponse> createModifyUserCannedReportsResponse(ModifyUserCannedReportsResponse value) {
        return new JAXBElement<ModifyUserCannedReportsResponse>(_ModifyUserCannedReportsResponse_QNAME, ModifyUserCannedReportsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCallVariablesGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createCallVariablesGroupResponse")
    public JAXBElement<CreateCallVariablesGroupResponse> createCreateCallVariablesGroupResponse(CreateCallVariablesGroupResponse value) {
        return new JAXBElement<CreateCallVariablesGroupResponse>(_CreateCallVariablesGroupResponse_QNAME, CreateCallVariablesGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoutReasonCodeNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "LogoutReasonCodeNotFoundFault")
    public JAXBElement<LogoutReasonCodeNotFoundException> createLogoutReasonCodeNotFoundFault(LogoutReasonCodeNotFoundException value) {
        return new JAXBElement<LogoutReasonCodeNotFoundException>(_LogoutReasonCodeNotFoundFault_QNAME, LogoutReasonCodeNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddSkillsToCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addSkillsToCampaignResponse")
    public JAXBElement<AddSkillsToCampaignResponse> createAddSkillsToCampaignResponse(AddSkillsToCampaignResponse value) {
        return new JAXBElement<AddSkillsToCampaignResponse>(_AddSkillsToCampaignResponse_QNAME, AddSkillsToCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignState }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignState")
    public JAXBElement<GetCampaignState> createGetCampaignState(GetCampaignState value) {
        return new JAXBElement<GetCampaignState>(_GetCampaignState_QNAME, GetCampaignState.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListsForCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getListsForCampaignResponse")
    public JAXBElement<GetListsForCampaignResponse> createGetListsForCampaignResponse(GetListsForCampaignResponse value) {
        return new JAXBElement<GetListsForCampaignResponse>(_GetListsForCampaignResponse_QNAME, GetListsForCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserInfo")
    public JAXBElement<GetUserInfo> createGetUserInfo(GetUserInfo value) {
        return new JAXBElement<GetUserInfo>(_GetUserInfo_QNAME, GetUserInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncDeleteRecordsFromListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "asyncDeleteRecordsFromListResponse")
    public JAXBElement<AsyncDeleteRecordsFromListResponse> createAsyncDeleteRecordsFromListResponse(AsyncDeleteRecordsFromListResponse value) {
        return new JAXBElement<AsyncDeleteRecordsFromListResponse>(_AsyncDeleteRecordsFromListResponse_QNAME, AsyncDeleteRecordsFromListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyInboundCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyInboundCampaignResponse")
    public JAXBElement<ModifyInboundCampaignResponse> createModifyInboundCampaignResponse(ModifyInboundCampaignResponse value) {
        return new JAXBElement<ModifyInboundCampaignResponse>(_ModifyInboundCampaignResponse_QNAME, ModifyInboundCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameDisposition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "renameDisposition")
    public JAXBElement<RenameDisposition> createRenameDisposition(RenameDisposition value) {
        return new JAXBElement<RenameDisposition>(_RenameDisposition_QNAME, RenameDisposition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromListResponse")
    public JAXBElement<DeleteFromListResponse> createDeleteFromListResponse(DeleteFromListResponse value) {
        return new JAXBElement<DeleteFromListResponse>(_DeleteFromListResponse_QNAME, DeleteFromListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOutboundCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getOutboundCampaignResponse")
    public JAXBElement<GetOutboundCampaignResponse> createGetOutboundCampaignResponse(GetOutboundCampaignResponse value) {
        return new JAXBElement<GetOutboundCampaignResponse>(_GetOutboundCampaignResponse_QNAME, GetOutboundCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentAuditReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentAuditReport")
    public JAXBElement<GetAgentAuditReport> createGetAgentAuditReport(GetAgentAuditReport value) {
        return new JAXBElement<GetAgentAuditReport>(_GetAgentAuditReport_QNAME, GetAgentAuditReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateContactField }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createContactField")
    public JAXBElement<CreateContactField> createCreateContactField(CreateContactField value) {
        return new JAXBElement<CreateContactField>(_CreateContactField_QNAME, CreateContactField.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileFilterOrder }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileFilterOrder")
    public JAXBElement<ModifyCampaignProfileFilterOrder> createModifyCampaignProfileFilterOrder(ModifyCampaignProfileFilterOrder value) {
        return new JAXBElement<ModifyCampaignProfileFilterOrder>(_ModifyCampaignProfileFilterOrder_QNAME, ModifyCampaignProfileFilterOrder.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDispositionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDispositionResponse")
    public JAXBElement<GetDispositionResponse> createGetDispositionResponse(GetDispositionResponse value) {
        return new JAXBElement<GetDispositionResponse>(_GetDispositionResponse_QNAME, GetDispositionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListIsNotAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ListIsNotAssignedFault")
    public JAXBElement<ListIsNotAssignedException> createListIsNotAssignedFault(ListIsNotAssignedException value) {
        return new JAXBElement<ListIsNotAssignedException>(_ListIsNotAssignedFault_QNAME, ListIsNotAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ListAlreadyExistsFault")
    public JAXBElement<ListAlreadyExistsException> createListAlreadyExistsFault(ListAlreadyExistsException value) {
        return new JAXBElement<ListAlreadyExistsException>(_ListAlreadyExistsFault_QNAME, ListAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserProfileResponse")
    public JAXBElement<ModifyUserProfileResponse> createModifyUserProfileResponse(ModifyUserProfileResponse value) {
        return new JAXBElement<ModifyUserProfileResponse>(_ModifyUserProfileResponse_QNAME, ModifyUserProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVCCConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getVCCConfigurationResponse")
    public JAXBElement<GetVCCConfigurationResponse> createGetVCCConfigurationResponse(GetVCCConfigurationResponse value) {
        return new JAXBElement<GetVCCConfigurationResponse>(_GetVCCConfigurationResponse_QNAME, GetVCCConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetVCCConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getVCCConfiguration")
    public JAXBElement<GetVCCConfiguration> createGetVCCConfiguration(GetVCCConfiguration value) {
        return new JAXBElement<GetVCCConfiguration>(_GetVCCConfiguration_QNAME, GetVCCConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDispositionsFtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateDispositionsFtp")
    public JAXBElement<UpdateDispositionsFtp> createUpdateDispositionsFtp(UpdateDispositionsFtp value) {
        return new JAXBElement<UpdateDispositionsFtp>(_UpdateDispositionsFtp_QNAME, UpdateDispositionsFtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDisposition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDisposition")
    public JAXBElement<GetDisposition> createGetDisposition(GetDisposition value) {
        return new JAXBElement<GetDisposition>(_GetDisposition_QNAME, GetDisposition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserVoicemailGreeting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserVoicemailGreeting")
    public JAXBElement<GetUserVoicemailGreeting> createGetUserVoicemailGreeting(GetUserVoicemailGreeting value) {
        return new JAXBElement<GetUserVoicemailGreeting>(_GetUserVoicemailGreeting_QNAME, GetUserVoicemailGreeting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDispositionsCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateDispositionsCsv")
    public JAXBElement<UpdateDispositionsCsv> createUpdateDispositionsCsv(UpdateDispositionsCsv value) {
        return new JAXBElement<UpdateDispositionsCsv>(_UpdateDispositionsCsv_QNAME, UpdateDispositionsCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateReasonCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createReasonCode")
    public JAXBElement<CreateReasonCode> createCreateReasonCode(CreateReasonCode value) {
        return new JAXBElement<CreateReasonCode>(_CreateReasonCode_QNAME, CreateReasonCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WrongPromptTypeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "WrongPromptTypeFault")
    public JAXBElement<WrongPromptTypeException> createWrongPromptTypeFault(WrongPromptTypeException value) {
        return new JAXBElement<WrongPromptTypeException>(_WrongPromptTypeFault_QNAME, WrongPromptTypeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DNISAlreadyAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DNISAlreadyAssignedFault")
    public JAXBElement<DNISAlreadyAssignedException> createDNISAlreadyAssignedFault(DNISAlreadyAssignedException value) {
        return new JAXBElement<DNISAlreadyAssignedException>(_DNISAlreadyAssignedFault_QNAME, DNISAlreadyAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link MissedArgumentException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "MissedArgumentFault")
    public JAXBElement<MissedArgumentException> createMissedArgumentFault(MissedArgumentException value) {
        return new JAXBElement<MissedArgumentException>(_MissedArgumentFault_QNAME, MissedArgumentException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNumbersToDncResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addNumbersToDncResponse")
    public JAXBElement<AddNumbersToDncResponse> createAddNumbersToDncResponse(AddNumbersToDncResponse value) {
        return new JAXBElement<AddNumbersToDncResponse>(_AddNumbersToDncResponse_QNAME, AddNumbersToDncResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAutodialCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAutodialCampaign")
    public JAXBElement<GetAutodialCampaign> createGetAutodialCampaign(GetAutodialCampaign value) {
        return new JAXBElement<GetAutodialCampaign>(_GetAutodialCampaign_QNAME, GetAutodialCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveSkillsFromCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeSkillsFromCampaignResponse")
    public JAXBElement<RemoveSkillsFromCampaignResponse> createRemoveSkillsFromCampaignResponse(RemoveSkillsFromCampaignResponse value) {
        return new JAXBElement<RemoveSkillsFromCampaignResponse>(_RemoveSkillsFromCampaignResponse_QNAME, RemoveSkillsFromCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToListFtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addToListFtpResponse")
    public JAXBElement<AddToListFtpResponse> createAddToListFtpResponse(AddToListFtpResponse value) {
        return new JAXBElement<AddToListFtpResponse>(_AddToListFtpResponse_QNAME, AddToListFtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDialingRulesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDialingRulesResponse")
    public JAXBElement<GetDialingRulesResponse> createGetDialingRulesResponse(GetDialingRulesResponse value) {
        return new JAXBElement<GetDialingRulesResponse>(_GetDialingRulesResponse_QNAME, GetDialingRulesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallLogReport }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallLogReport")
    public JAXBElement<GetCallLogReport> createGetCallLogReport(GetCallLogReport value) {
        return new JAXBElement<GetCallLogReport>(_GetCallLogReport_QNAME, GetCallLogReport.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AccessDisallowedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "AccessDisallowedFault")
    public JAXBElement<AccessDisallowedException> createAccessDisallowedFault(AccessDisallowedException value) {
        return new JAXBElement<AccessDisallowedException>(_AccessDisallowedFault_QNAME, AccessDisallowedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPromptWavResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addPromptWavResponse")
    public JAXBElement<AddPromptWavResponse> createAddPromptWavResponse(AddPromptWavResponse value) {
        return new JAXBElement<AddPromptWavResponse>(_AddPromptWavResponse_QNAME, AddPromptWavResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDispositionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeDispositionResponse")
    public JAXBElement<RemoveDispositionResponse> createRemoveDispositionResponse(RemoveDispositionResponse value) {
        return new JAXBElement<RemoveDispositionResponse>(_RemoveDispositionResponse_QNAME, RemoveDispositionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPromptWav }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyPromptWav")
    public JAXBElement<ModifyPromptWav> createModifyPromptWav(ModifyPromptWav value) {
        return new JAXBElement<ModifyPromptWav>(_ModifyPromptWav_QNAME, ModifyPromptWav.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyOutboundCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyOutboundCampaignResponse")
    public JAXBElement<ModifyOutboundCampaignResponse> createModifyOutboundCampaignResponse(ModifyOutboundCampaignResponse value) {
        return new JAXBElement<ModifyOutboundCampaignResponse>(_ModifyOutboundCampaignResponse_QNAME, ModifyOutboundCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserProfileSkills }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserProfileSkills")
    public JAXBElement<ModifyUserProfileSkills> createModifyUserProfileSkills(ModifyUserProfileSkills value) {
        return new JAXBElement<ModifyUserProfileSkills>(_ModifyUserProfileSkills_QNAME, ModifyUserProfileSkills.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactsFtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateContactsFtpResponse")
    public JAXBElement<UpdateContactsFtpResponse> createUpdateContactsFtpResponse(UpdateContactsFtpResponse value) {
        return new JAXBElement<UpdateContactsFtpResponse>(_UpdateContactsFtpResponse_QNAME, UpdateContactsFtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateAutodialCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createAutodialCampaignResponse")
    public JAXBElement<CreateAutodialCampaignResponse> createCreateAutodialCampaignResponse(CreateAutodialCampaignResponse value) {
        return new JAXBElement<CreateAutodialCampaignResponse>(_CreateAutodialCampaignResponse_QNAME, CreateAutodialCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "stopCampaignResponse")
    public JAXBElement<StopCampaignResponse> createStopCampaignResponse(StopCampaignResponse value) {
        return new JAXBElement<StopCampaignResponse>(_StopCampaignResponse_QNAME, StopCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyPromptTTSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyPromptTTSResponse")
    public JAXBElement<ModifyPromptTTSResponse> createModifyPromptTTSResponse(ModifyPromptTTSResponse value) {
        return new JAXBElement<ModifyPromptTTSResponse>(_ModifyPromptTTSResponse_QNAME, ModifyPromptTTSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addToList")
    public JAXBElement<AddToList> createAddToList(AddToList value) {
        return new JAXBElement<AddToList>(_AddToList_QNAME, AddToList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserCantBeDeletedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserCantBeDeletedFault")
    public JAXBElement<UserCantBeDeletedException> createUserCantBeDeletedFault(UserCantBeDeletedException value) {
        return new JAXBElement<UserCantBeDeletedException>(_UserCantBeDeletedFault_QNAME, UserCantBeDeletedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveListsFromCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeListsFromCampaign")
    public JAXBElement<RemoveListsFromCampaign> createRemoveListsFromCampaign(RemoveListsFromCampaign value) {
        return new JAXBElement<RemoveListsFromCampaign>(_RemoveListsFromCampaign_QNAME, RemoveListsFromCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResultIsNotReadyException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ResultIsNotReadyFault")
    public JAXBElement<ResultIsNotReadyException> createResultIsNotReadyFault(ResultIsNotReadyException value) {
        return new JAXBElement<ResultIsNotReadyException>(_ResultIsNotReadyFault_QNAME, ResultIsNotReadyException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveListsFromCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeListsFromCampaignResponse")
    public JAXBElement<RemoveListsFromCampaignResponse> createRemoveListsFromCampaignResponse(RemoveListsFromCampaignResponse value) {
        return new JAXBElement<RemoveListsFromCampaignResponse>(_RemoveListsFromCampaignResponse_QNAME, RemoveListsFromCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyVCCConfiguration }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyVCCConfiguration")
    public JAXBElement<ModifyVCCConfiguration> createModifyVCCConfiguration(ModifyVCCConfiguration value) {
        return new JAXBElement<ModifyVCCConfiguration>(_ModifyVCCConfiguration_QNAME, ModifyVCCConfiguration.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToListCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addToListCsv")
    public JAXBElement<AddToListCsv> createAddToListCsv(AddToListCsv value) {
        return new JAXBElement<AddToListCsv>(_AddToListCsv_QNAME, AddToListCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateInboundCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createInboundCampaign")
    public JAXBElement<CreateInboundCampaign> createCreateInboundCampaign(CreateInboundCampaign value) {
        return new JAXBElement<CreateInboundCampaign>(_CreateInboundCampaign_QNAME, CreateInboundCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListsInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getListsInfoResponse")
    public JAXBElement<GetListsInfoResponse> createGetListsInfoResponse(GetListsInfoResponse value) {
        return new JAXBElement<GetListsInfoResponse>(_GetListsInfoResponse_QNAME, GetListsInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAlreadyLoggedInException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserAlreadyLoggedInFault")
    public JAXBElement<UserAlreadyLoggedInException> createUserAlreadyLoggedInFault(UserAlreadyLoggedInException value) {
        return new JAXBElement<UserAlreadyLoggedInException>(_UserAlreadyLoggedInFault_QNAME, UserAlreadyLoggedInException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReasonCodeCountLimitExceededException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ReasonCodeCountLimitExceededFault")
    public JAXBElement<ReasonCodeCountLimitExceededException> createReasonCodeCountLimitExceededFault(ReasonCodeCountLimitExceededException value) {
        return new JAXBElement<ReasonCodeCountLimitExceededException>(_ReasonCodeCountLimitExceededFault_QNAME, ReasonCodeCountLimitExceededException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyIVRScriptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyIVRScriptResponse")
    public JAXBElement<ModifyIVRScriptResponse> createModifyIVRScriptResponse(ModifyIVRScriptResponse value) {
        return new JAXBElement<ModifyIVRScriptResponse>(_ModifyIVRScriptResponse_QNAME, ModifyIVRScriptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCampaignProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCampaignProfileResponse")
    public JAXBElement<DeleteCampaignProfileResponse> createDeleteCampaignProfileResponse(DeleteCampaignProfileResponse value) {
        return new JAXBElement<DeleteCampaignProfileResponse>(_DeleteCampaignProfileResponse_QNAME, DeleteCampaignProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateReasonCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createReasonCodeResponse")
    public JAXBElement<CreateReasonCodeResponse> createCreateReasonCodeResponse(CreateReasonCodeResponse value) {
        return new JAXBElement<CreateReasonCodeResponse>(_CreateReasonCodeResponse_QNAME, CreateReasonCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReportResultResponse")
    public JAXBElement<GetReportResultResponse> createGetReportResultResponse(GetReportResultResponse value) {
        return new JAXBElement<GetReportResultResponse>(_GetReportResultResponse_QNAME, GetReportResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReportNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ReportNotFoundFault")
    public JAXBElement<ReportNotFoundException> createReportNotFoundFault(ReportNotFoundException value) {
        return new JAXBElement<ReportNotFoundException>(_ReportNotFoundFault_QNAME, ReportNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPromptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getPromptResponse")
    public JAXBElement<GetPromptResponse> createGetPromptResponse(GetPromptResponse value) {
        return new JAXBElement<GetPromptResponse>(_GetPromptResponse_QNAME, GetPromptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExtensionAlreadyInUseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ExtensionAlreadyInUseFault")
    public JAXBElement<ExtensionAlreadyInUseException> createExtensionAlreadyInUseFault(ExtensionAlreadyInUseException value) {
        return new JAXBElement<ExtensionAlreadyInUseException>(_ExtensionAlreadyInUseFault_QNAME, ExtensionAlreadyInUseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToListFtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addToListFtp")
    public JAXBElement<AddToListFtp> createAddToListFtp(AddToListFtp value) {
        return new JAXBElement<AddToListFtp>(_AddToListFtp_QNAME, AddToListFtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromListFtpResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromListFtpResponse")
    public JAXBElement<DeleteFromListFtpResponse> createDeleteFromListFtpResponse(DeleteFromListFtpResponse value) {
        return new JAXBElement<DeleteFromListFtpResponse>(_DeleteFromListFtpResponse_QNAME, DeleteFromListFtpResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ScheduleNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ScheduleNotFoundFault")
    public JAXBElement<ScheduleNotFoundException> createScheduleNotFoundFault(ScheduleNotFoundException value) {
        return new JAXBElement<ScheduleNotFoundException>(_ScheduleNotFoundFault_QNAME, ScheduleNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteListResponse")
    public JAXBElement<DeleteListResponse> createDeleteListResponse(DeleteListResponse value) {
        return new JAXBElement<DeleteListResponse>(_DeleteListResponse_QNAME, DeleteListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ExecutionRestrictionException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ExecutionRestrictionFault")
    public JAXBElement<ExecutionRestrictionException> createExecutionRestrictionFault(ExecutionRestrictionException value) {
        return new JAXBElement<ExecutionRestrictionException>(_ExecutionRestrictionFault_QNAME, ExecutionRestrictionException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DispositionIsNotAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DispositionIsNotAssisgnedFault")
    public JAXBElement<DispositionIsNotAssignedException> createDispositionIsNotAssisgnedFault(DispositionIsNotAssignedException value) {
        return new JAXBElement<DispositionIsNotAssignedException>(_DispositionIsNotAssisgnedFault_QNAME, DispositionIsNotAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserProfile")
    public JAXBElement<ModifyUserProfile> createModifyUserProfile(ModifyUserProfile value) {
        return new JAXBElement<ModifyUserProfile>(_ModifyUserProfile_QNAME, ModifyUserProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAllFromList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteAllFromList")
    public JAXBElement<DeleteAllFromList> createDeleteAllFromList(DeleteAllFromList value) {
        return new JAXBElement<DeleteAllFromList>(_DeleteAllFromList_QNAME, DeleteAllFromList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInboundCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getInboundCampaign")
    public JAXBElement<GetInboundCampaign> createGetInboundCampaign(GetInboundCampaign value) {
        return new JAXBElement<GetInboundCampaign>(_GetInboundCampaign_QNAME, GetInboundCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateWebConnector }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createWebConnector")
    public JAXBElement<CreateWebConnector> createCreateWebConnector(CreateWebConnector value) {
        return new JAXBElement<CreateWebConnector>(_CreateWebConnector_QNAME, CreateWebConnector.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListCantBeRemovedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ListCantBeRemovedFault")
    public JAXBElement<ListCantBeRemovedException> createListCantBeRemovedFault(ListCantBeRemovedException value) {
        return new JAXBElement<ListCantBeRemovedException>(_ListCantBeRemovedFault_QNAME, ListCantBeRemovedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyDispositionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyDispositionResponse")
    public JAXBElement<ModifyDispositionResponse> createModifyDispositionResponse(ModifyDispositionResponse value) {
        return new JAXBElement<ModifyDispositionResponse>(_ModifyDispositionResponse_QNAME, ModifyDispositionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUsersInfo")
    public JAXBElement<GetUsersInfo> createGetUsersInfo(GetUsersInfo value) {
        return new JAXBElement<GetUsersInfo>(_GetUsersInfo_QNAME, GetUsersInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CantModifyObjectException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "CantModifyObjectFault")
    public JAXBElement<CantModifyObjectException> createCantModifyObjectFault(CantModifyObjectException value) {
        return new JAXBElement<CantModifyObjectException>(_CantModifyObjectFault_QNAME, CantModifyObjectException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CampaignStateUpdateException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "CampaignStateUpdateFault")
    public JAXBElement<CampaignStateUpdateException> createCampaignStateUpdateFault(CampaignStateUpdateException value) {
        return new JAXBElement<CampaignStateUpdateException>(_CampaignStateUpdateFault_QNAME, CampaignStateUpdateException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetDialingRulesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setDialingRulesResponse")
    public JAXBElement<SetDialingRulesResponse> createSetDialingRulesResponse(SetDialingRulesResponse value) {
        return new JAXBElement<SetDialingRulesResponse>(_SetDialingRulesResponse_QNAME, SetDialingRulesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileDispositions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileDispositions")
    public JAXBElement<ModifyCampaignProfileDispositions> createModifyCampaignProfileDispositions(ModifyCampaignProfileDispositions value) {
        return new JAXBElement<ModifyCampaignProfileDispositions>(_ModifyCampaignProfileDispositions_QNAME, ModifyCampaignProfileDispositions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDNISFromCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeDNISFromCampaign")
    public JAXBElement<RemoveDNISFromCampaign> createRemoveDNISFromCampaign(RemoveDNISFromCampaign value) {
        return new JAXBElement<RemoveDNISFromCampaign>(_RemoveDNISFromCampaign_QNAME, RemoveDNISFromCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromListCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromListCsv")
    public JAXBElement<DeleteFromListCsv> createDeleteFromListCsv(DeleteFromListCsv value) {
        return new JAXBElement<DeleteFromListCsv>(_DeleteFromListCsv_QNAME, DeleteFromListCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCampaign")
    public JAXBElement<DeleteCampaign> createDeleteCampaign(DeleteCampaign value) {
        return new JAXBElement<DeleteCampaign>(_DeleteCampaign_QNAME, DeleteCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserDoesntHaveSkillException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserDoesntHaveSkillFault")
    public JAXBElement<UserDoesntHaveSkillException> createUserDoesntHaveSkillFault(UserDoesntHaveSkillException value) {
        return new JAXBElement<UserDoesntHaveSkillException>(_UserDoesntHaveSkillFault_QNAME, UserDoesntHaveSkillException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPromptWavInline }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addPromptWavInline")
    public JAXBElement<AddPromptWavInline> createAddPromptWavInline(AddPromptWavInline value) {
        return new JAXBElement<AddPromptWavInline>(_AddPromptWavInline_QNAME, AddPromptWavInline.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsImportRunning }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "isImportRunning")
    public JAXBElement<IsImportRunning> createIsImportRunning(IsImportRunning value) {
        return new JAXBElement<IsImportRunning>(_IsImportRunning_QNAME, IsImportRunning.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromListFtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromListFtp")
    public JAXBElement<DeleteFromListFtp> createDeleteFromListFtp(DeleteFromListFtp value) {
        return new JAXBElement<DeleteFromListFtp>(_DeleteFromListFtp_QNAME, DeleteFromListFtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCallVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createCallVariable")
    public JAXBElement<CreateCallVariable> createCreateCallVariable(CreateCallVariable value) {
        return new JAXBElement<CreateCallVariable>(_CreateCallVariable_QNAME, CreateCallVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckDncForNumbersResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "checkDncForNumbersResponse")
    public JAXBElement<CheckDncForNumbersResponse> createCheckDncForNumbersResponse(CheckDncForNumbersResponse value) {
        return new JAXBElement<CheckDncForNumbersResponse>(_CheckDncForNumbersResponse_QNAME, CheckDncForNumbersResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReasonCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReasonCode")
    public JAXBElement<GetReasonCode> createGetReasonCode(GetReasonCode value) {
        return new JAXBElement<GetReasonCode>(_GetReasonCode_QNAME, GetReasonCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateDisposition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createDisposition")
    public JAXBElement<CreateDisposition> createCreateDisposition(CreateDisposition value) {
        return new JAXBElement<CreateDisposition>(_CreateDisposition_QNAME, CreateDisposition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserVoicemailGreetingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserVoicemailGreetingResponse")
    public JAXBElement<GetUserVoicemailGreetingResponse> createGetUserVoicemailGreetingResponse(GetUserVoicemailGreetingResponse value) {
        return new JAXBElement<GetUserVoicemailGreetingResponse>(_GetUserVoicemailGreetingResponse_QNAME, GetUserVoicemailGreetingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CrmFieldNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "CrmFieldNotFoundFault")
    public JAXBElement<CrmFieldNotFoundException> createCrmFieldNotFoundFault(CrmFieldNotFoundException value) {
        return new JAXBElement<CrmFieldNotFoundException>(_CrmFieldNotFoundFault_QNAME, CrmFieldNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportResultCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReportResultCsvResponse")
    public JAXBElement<GetReportResultCsvResponse> createGetReportResultCsvResponse(GetReportResultCsvResponse value) {
        return new JAXBElement<GetReportResultCsvResponse>(_GetReportResultCsvResponse_QNAME, GetReportResultCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDispositionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDispositionsResponse")
    public JAXBElement<GetDispositionsResponse> createGetDispositionsResponse(GetDispositionsResponse value) {
        return new JAXBElement<GetDispositionsResponse>(_GetDispositionsResponse_QNAME, GetDispositionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteUserProfile")
    public JAXBElement<DeleteUserProfile> createDeleteUserProfile(DeleteUserProfile value) {
        return new JAXBElement<DeleteUserProfile>(_DeleteUserProfile_QNAME, DeleteUserProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ObjectInUseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ObjectInUseFault")
    public JAXBElement<ObjectInUseException> createObjectInUseFault(ObjectInUseException value) {
        return new JAXBElement<ObjectInUseException>(_ObjectInUseFault_QNAME, ObjectInUseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteReasonCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteReasonCode")
    public JAXBElement<DeleteReasonCode> createDeleteReasonCode(DeleteReasonCode value) {
        return new JAXBElement<DeleteReasonCode>(_DeleteReasonCode_QNAME, DeleteReasonCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportResultCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReportResultCsv")
    public JAXBElement<GetReportResultCsv> createGetReportResultCsv(GetReportResultCsv value) {
        return new JAXBElement<GetReportResultCsv>(_GetReportResultCsv_QNAME, GetReportResultCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCallVariablesGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCallVariablesGroup")
    public JAXBElement<DeleteCallVariablesGroup> createDeleteCallVariablesGroup(DeleteCallVariablesGroup value) {
        return new JAXBElement<DeleteCallVariablesGroup>(_DeleteCallVariablesGroup_QNAME, DeleteCallVariablesGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyInboundCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyInboundCampaign")
    public JAXBElement<ModifyInboundCampaign> createModifyInboundCampaign(ModifyInboundCampaign value) {
        return new JAXBElement<ModifyInboundCampaign>(_ModifyInboundCampaign_QNAME, ModifyInboundCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetSkillVoicemailGreetingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setSkillVoicemailGreetingResponse")
    public JAXBElement<SetSkillVoicemailGreetingResponse> createSetSkillVoicemailGreetingResponse(SetSkillVoicemailGreetingResponse value) {
        return new JAXBElement<SetSkillVoicemailGreetingResponse>(_SetSkillVoicemailGreetingResponse_QNAME, SetSkillVoicemailGreetingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "renameCampaignResponse")
    public JAXBElement<RenameCampaignResponse> createRenameCampaignResponse(RenameCampaignResponse value) {
        return new JAXBElement<RenameCampaignResponse>(_RenameCampaignResponse_QNAME, RenameCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncUpdateCrmRecordsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "asyncUpdateCrmRecordsResponse")
    public JAXBElement<AsyncUpdateCrmRecordsResponse> createAsyncUpdateCrmRecordsResponse(AsyncUpdateCrmRecordsResponse value) {
        return new JAXBElement<AsyncUpdateCrmRecordsResponse>(_AsyncUpdateCrmRecordsResponse_QNAME, AsyncUpdateCrmRecordsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentGroupsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentGroupsResponse")
    public JAXBElement<GetAgentGroupsResponse> createGetAgentGroupsResponse(GetAgentGroupsResponse value) {
        return new JAXBElement<GetAgentGroupsResponse>(_GetAgentGroupsResponse_QNAME, GetAgentGroupsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyWebConnector }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyWebConnector")
    public JAXBElement<ModifyWebConnector> createModifyWebConnector(ModifyWebConnector value) {
        return new JAXBElement<ModifyWebConnector>(_ModifyWebConnector_QNAME, ModifyWebConnector.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StopCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "stopCampaign")
    public JAXBElement<StopCampaign> createStopCampaign(StopCampaign value) {
        return new JAXBElement<StopCampaign>(_StopCampaign_QNAME, StopCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentGroups }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentGroups")
    public JAXBElement<GetAgentGroups> createGetAgentGroups(GetAgentGroups value) {
        return new JAXBElement<GetAgentGroups>(_GetAgentGroups_QNAME, GetAgentGroups.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillInfoResponse")
    public JAXBElement<GetSkillInfoResponse> createGetSkillInfoResponse(GetSkillInfoResponse value) {
        return new JAXBElement<GetSkillInfoResponse>(_GetSkillInfoResponse_QNAME, GetSkillInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteUserProfileResponse")
    public JAXBElement<DeleteUserProfileResponse> createDeleteUserProfileResponse(DeleteUserProfileResponse value) {
        return new JAXBElement<DeleteUserProfileResponse>(_DeleteUserProfileResponse_QNAME, DeleteUserProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserVoicemailGreetingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setUserVoicemailGreetingResponse")
    public JAXBElement<SetUserVoicemailGreetingResponse> createSetUserVoicemailGreetingResponse(SetUserVoicemailGreetingResponse value) {
        return new JAXBElement<SetUserVoicemailGreetingResponse>(_SetUserVoicemailGreetingResponse_QNAME, SetUserVoicemailGreetingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetCampaignDispositionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "resetCampaignDispositionsResponse")
    public JAXBElement<ResetCampaignDispositionsResponse> createResetCampaignDispositionsResponse(ResetCampaignDispositionsResponse value) {
        return new JAXBElement<ResetCampaignDispositionsResponse>(_ResetCampaignDispositionsResponse_QNAME, ResetCampaignDispositionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkills }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkills")
    public JAXBElement<GetSkills> createGetSkills(GetSkills value) {
        return new JAXBElement<GetSkills>(_GetSkills_QNAME, GetSkills.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignProfilesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignProfilesResponse")
    public JAXBElement<GetCampaignProfilesResponse> createGetCampaignProfilesResponse(GetCampaignProfilesResponse value) {
        return new JAXBElement<GetCampaignProfilesResponse>(_GetCampaignProfilesResponse_QNAME, GetCampaignProfilesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePromptResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deletePromptResponse")
    public JAXBElement<DeletePromptResponse> createDeletePromptResponse(DeletePromptResponse value) {
        return new JAXBElement<DeletePromptResponse>(_DeletePromptResponse_QNAME, DeletePromptResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TooManyExtensionsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "TooManyExtensionsFault")
    public JAXBElement<TooManyExtensionsException> createTooManyExtensionsFault(TooManyExtensionsException value) {
        return new JAXBElement<TooManyExtensionsException>(_TooManyExtensionsFault_QNAME, TooManyExtensionsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSkill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteSkill")
    public JAXBElement<DeleteSkill> createDeleteSkill(DeleteSkill value) {
        return new JAXBElement<DeleteSkill>(_DeleteSkill_QNAME, DeleteSkill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAlreadyAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ListAlreadyAssignedFault")
    public JAXBElement<ListAlreadyAssignedException> createListAlreadyAssignedFault(ListAlreadyAssignedException value) {
        return new JAXBElement<ListAlreadyAssignedException>(_ListAlreadyAssignedFault_QNAME, ListAlreadyAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetContactFields }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getContactFields")
    public JAXBElement<GetContactFields> createGetContactFields(GetContactFields value) {
        return new JAXBElement<GetContactFields>(_GetContactFields_QNAME, GetContactFields.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PromptNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "PromptNotFoundFault")
    public JAXBElement<PromptNotFoundException> createPromptNotFoundFault(PromptNotFoundException value) {
        return new JAXBElement<PromptNotFoundException>(_PromptNotFoundFault_QNAME, PromptNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyAgentGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyAgentGroupResponse")
    public JAXBElement<ModifyAgentGroupResponse> createModifyAgentGroupResponse(ModifyAgentGroupResponse value) {
        return new JAXBElement<ModifyAgentGroupResponse>(_ModifyAgentGroupResponse_QNAME, ModifyAgentGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserGeneralInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserGeneralInfoResponse")
    public JAXBElement<GetUserGeneralInfoResponse> createGetUserGeneralInfoResponse(GetUserGeneralInfoResponse value) {
        return new JAXBElement<GetUserGeneralInfoResponse>(_GetUserGeneralInfoResponse_QNAME, GetUserGeneralInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "startCampaign")
    public JAXBElement<StartCampaign> createStartCampaign(StartCampaign value) {
        return new JAXBElement<StartCampaign>(_StartCampaign_QNAME, StartCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link StartCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "startCampaignResponse")
    public JAXBElement<StartCampaignResponse> createStartCampaignResponse(StartCampaignResponse value) {
        return new JAXBElement<StartCampaignResponse>(_StartCampaignResponse_QNAME, StartCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ImportInProgressException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ImportInProgressFault")
    public JAXBElement<ImportInProgressException> createImportInProgressFault(ImportInProgressException value) {
        return new JAXBElement<ImportInProgressException>(_ImportInProgressFault_QNAME, ImportInProgressException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncDeleteRecordsFromList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "asyncDeleteRecordsFromList")
    public JAXBElement<AsyncDeleteRecordsFromList> createAsyncDeleteRecordsFromList(AsyncDeleteRecordsFromList value) {
        return new JAXBElement<AsyncDeleteRecordsFromList>(_AsyncDeleteRecordsFromList_QNAME, AsyncDeleteRecordsFromList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserProfileResponse")
    public JAXBElement<GetUserProfileResponse> createGetUserProfileResponse(GetUserProfileResponse value) {
        return new JAXBElement<GetUserProfileResponse>(_GetUserProfileResponse_QNAME, GetUserProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCallVariablesGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCallVariablesGroupResponse")
    public JAXBElement<ModifyCallVariablesGroupResponse> createModifyCallVariablesGroupResponse(ModifyCallVariablesGroupResponse value) {
        return new JAXBElement<ModifyCallVariablesGroupResponse>(_ModifyCallVariablesGroupResponse_QNAME, ModifyCallVariablesGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListsInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getListsInfo")
    public JAXBElement<GetListsInfo> createGetListsInfo(GetListsInfo value) {
        return new JAXBElement<GetListsInfo>(_GetListsInfo_QNAME, GetListsInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NotReadyReasonCodeNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "NotReadyReasonCodeNotFoundFault")
    public JAXBElement<NotReadyReasonCodeNotFoundException> createNotReadyReasonCodeNotFoundFault(NotReadyReasonCodeNotFoundException value) {
        return new JAXBElement<NotReadyReasonCodeNotFoundException>(_NotReadyReasonCodeNotFoundFault_QNAME, NotReadyReasonCodeNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveNumbersFromDnc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeNumbersFromDnc")
    public JAXBElement<RemoveNumbersFromDnc> createRemoveNumbersFromDnc(RemoveNumbersFromDnc value) {
        return new JAXBElement<RemoveNumbersFromDnc>(_RemoveNumbersFromDnc_QNAME, RemoveNumbersFromDnc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAlreadyHasSkillException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserAlreadyHasSkillFault")
    public JAXBElement<UserAlreadyHasSkillException> createUserAlreadyHasSkillFault(UserAlreadyHasSkillException value) {
        return new JAXBElement<UserAlreadyHasSkillException>(_UserAlreadyHasSkillFault_QNAME, UserAlreadyHasSkillException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaigns }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaigns")
    public JAXBElement<GetCampaigns> createGetCampaigns(GetCampaigns value) {
        return new JAXBElement<GetCampaigns>(_GetCampaigns_QNAME, GetCampaigns.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCallVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createCallVariableResponse")
    public JAXBElement<CreateCallVariableResponse> createCreateCallVariableResponse(CreateCallVariableResponse value) {
        return new JAXBElement<CreateCallVariableResponse>(_CreateCallVariableResponse_QNAME, CreateCallVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCallVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCallVariable")
    public JAXBElement<ModifyCallVariable> createModifyCallVariable(ModifyCallVariable value) {
        return new JAXBElement<ModifyCallVariable>(_ModifyCallVariable_QNAME, ModifyCallVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentAuditReportCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentAuditReportCsvResponse")
    public JAXBElement<GetAgentAuditReportCsvResponse> createGetAgentAuditReportCsvResponse(GetAgentAuditReportCsvResponse value) {
        return new JAXBElement<GetAgentAuditReportCsvResponse>(_GetAgentAuditReportCsvResponse_QNAME, GetAgentAuditReportCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersGeneralInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUsersGeneralInfoResponse")
    public JAXBElement<GetUsersGeneralInfoResponse> createGetUsersGeneralInfoResponse(GetUsersGeneralInfoResponse value) {
        return new JAXBElement<GetUsersGeneralInfoResponse>(_GetUsersGeneralInfoResponse_QNAME, GetUsersGeneralInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOutboundCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getOutboundCampaign")
    public JAXBElement<GetOutboundCampaign> createGetOutboundCampaign(GetOutboundCampaign value) {
        return new JAXBElement<GetOutboundCampaign>(_GetOutboundCampaign_QNAME, GetOutboundCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignProfileDispositionsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignProfileDispositionsResponse")
    public JAXBElement<GetCampaignProfileDispositionsResponse> createGetCampaignProfileDispositionsResponse(GetCampaignProfileDispositionsResponse value) {
        return new JAXBElement<GetCampaignProfileDispositionsResponse>(_GetCampaignProfileDispositionsResponse_QNAME, GetCampaignProfileDispositionsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactsCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateContactsCsv")
    public JAXBElement<UpdateContactsCsv> createUpdateContactsCsv(UpdateContactsCsv value) {
        return new JAXBElement<UpdateContactsCsv>(_UpdateContactsCsv_QNAME, UpdateContactsCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateInboundCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createInboundCampaignResponse")
    public JAXBElement<CreateInboundCampaignResponse> createCreateInboundCampaignResponse(CreateInboundCampaignResponse value) {
        return new JAXBElement<CreateInboundCampaignResponse>(_CreateInboundCampaignResponse_QNAME, CreateInboundCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignDNISList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignDNISList")
    public JAXBElement<GetCampaignDNISList> createGetCampaignDNISList(GetCampaignDNISList value) {
        return new JAXBElement<GetCampaignDNISList>(_GetCampaignDNISList_QNAME, GetCampaignDNISList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyContactField }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyContactField")
    public JAXBElement<ModifyContactField> createModifyContactField(ModifyContactField value) {
        return new JAXBElement<ModifyContactField>(_ModifyContactField_QNAME, ModifyContactField.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetInboundCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getInboundCampaignResponse")
    public JAXBElement<GetInboundCampaignResponse> createGetInboundCampaignResponse(GetInboundCampaignResponse value) {
        return new JAXBElement<GetInboundCampaignResponse>(_GetInboundCampaignResponse_QNAME, GetInboundCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCampaignProfile }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createCampaignProfile")
    public JAXBElement<CreateCampaignProfile> createCreateCampaignProfile(CreateCampaignProfile value) {
        return new JAXBElement<CreateCampaignProfile>(_CreateCampaignProfile_QNAME, CreateCampaignProfile.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetUserVoicemailGreeting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setUserVoicemailGreeting")
    public JAXBElement<SetUserVoicemailGreeting> createSetUserVoicemailGreeting(SetUserVoicemailGreeting value) {
        return new JAXBElement<SetUserVoicemailGreeting>(_SetUserVoicemailGreeting_QNAME, SetUserVoicemailGreeting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallVariablesResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallVariablesResponse")
    public JAXBElement<GetCallVariablesResponse> createGetCallVariablesResponse(GetCallVariablesResponse value) {
        return new JAXBElement<GetCallVariablesResponse>(_GetCallVariablesResponse_QNAME, GetCallVariablesResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserNotFoundFault")
    public JAXBElement<UserNotFoundException> createUserNotFoundFault(UserNotFoundException value) {
        return new JAXBElement<UserNotFoundException>(_UserNotFoundFault_QNAME, UserNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWebConnectors }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getWebConnectors")
    public JAXBElement<GetWebConnectors> createGetWebConnectors(GetWebConnectors value) {
        return new JAXBElement<GetWebConnectors>(_GetWebConnectors_QNAME, GetWebConnectors.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyReasonCode }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyReasonCode")
    public JAXBElement<ModifyReasonCode> createModifyReasonCode(ModifyReasonCode value) {
        return new JAXBElement<ModifyReasonCode>(_ModifyReasonCode_QNAME, ModifyReasonCode.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPromptTTSResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addPromptTTSResponse")
    public JAXBElement<AddPromptTTSResponse> createAddPromptTTSResponse(AddPromptTTSResponse value) {
        return new JAXBElement<AddPromptTTSResponse>(_AddPromptTTSResponse_QNAME, AddPromptTTSResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ReasonCodeNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ReasonCodeNotFoundFault")
    public JAXBElement<ReasonCodeNotFoundException> createReasonCodeNotFoundFault(ReasonCodeNotFoundException value) {
        return new JAXBElement<ReasonCodeNotFoundException>(_ReasonCodeNotFoundFault_QNAME, ReasonCodeNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContactsCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateContactsCsvResponse")
    public JAXBElement<UpdateContactsCsvResponse> createUpdateContactsCsvResponse(UpdateContactsCsvResponse value) {
        return new JAXBElement<UpdateContactsCsvResponse>(_UpdateContactsCsvResponse_QNAME, UpdateContactsCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAllFromListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteAllFromListResponse")
    public JAXBElement<DeleteAllFromListResponse> createDeleteAllFromListResponse(DeleteAllFromListResponse value) {
        return new JAXBElement<DeleteAllFromListResponse>(_DeleteAllFromListResponse_QNAME, DeleteAllFromListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetIVRScripts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getIVRScripts")
    public JAXBElement<GetIVRScripts> createGetIVRScripts(GetIVRScripts value) {
        return new JAXBElement<GetIVRScripts>(_GetIVRScripts_QNAME, GetIVRScripts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InternalImportException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "InternalImportFault")
    public JAXBElement<InternalImportException> createInternalImportFault(InternalImportException value) {
        return new JAXBElement<InternalImportException>(_InternalImportFault_QNAME, InternalImportException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDispositionsFromCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeDispositionsFromCampaign")
    public JAXBElement<RemoveDispositionsFromCampaign> createRemoveDispositionsFromCampaign(RemoveDispositionsFromCampaign value) {
        return new JAXBElement<RemoveDispositionsFromCampaign>(_RemoveDispositionsFromCampaign_QNAME, RemoveDispositionsFromCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillVoicemailGreetingResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillVoicemailGreetingResponse")
    public JAXBElement<GetSkillVoicemailGreetingResponse> createGetSkillVoicemailGreetingResponse(GetSkillVoicemailGreetingResponse value) {
        return new JAXBElement<GetSkillVoicemailGreetingResponse>(_GetSkillVoicemailGreetingResponse_QNAME, GetSkillVoicemailGreetingResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SkillAlreadyAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "SkillAlreadyAssignedFault")
    public JAXBElement<SkillAlreadyAssignedException> createSkillAlreadyAssignedFault(SkillAlreadyAssignedException value) {
        return new JAXBElement<SkillAlreadyAssignedException>(_SkillAlreadyAssignedFault_QNAME, SkillAlreadyAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileCrmCriteriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileCrmCriteriaResponse")
    public JAXBElement<ModifyCampaignProfileCrmCriteriaResponse> createModifyCampaignProfileCrmCriteriaResponse(ModifyCampaignProfileCrmCriteriaResponse value) {
        return new JAXBElement<ModifyCampaignProfileCrmCriteriaResponse>(_ModifyCampaignProfileCrmCriteriaResponse_QNAME, ModifyCampaignProfileCrmCriteriaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCampaignProfiles }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCampaignProfiles")
    public JAXBElement<GetCampaignProfiles> createGetCampaignProfiles(GetCampaignProfiles value) {
        return new JAXBElement<GetCampaignProfiles>(_GetCampaignProfiles_QNAME, GetCampaignProfiles.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDNISToCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addDNISToCampaignResponse")
    public JAXBElement<AddDNISToCampaignResponse> createAddDNISToCampaignResponse(AddDNISToCampaignResponse value) {
        return new JAXBElement<AddDNISToCampaignResponse>(_AddDNISToCampaignResponse_QNAME, AddDNISToCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCallVariable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCallVariable")
    public JAXBElement<DeleteCallVariable> createDeleteCallVariable(DeleteCallVariable value) {
        return new JAXBElement<DeleteCallVariable>(_DeleteCallVariable_QNAME, DeleteCallVariable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link InvalidUserDataException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "InvalidUserDataFault")
    public JAXBElement<InvalidUserDataException> createInvalidUserDataFault(InvalidUserDataException value) {
        return new JAXBElement<InvalidUserDataException>(_InvalidUserDataFault_QNAME, InvalidUserDataException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentAuditReportCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentAuditReportCsv")
    public JAXBElement<GetAgentAuditReportCsv> createGetAgentAuditReportCsv(GetAgentAuditReportCsv value) {
        return new JAXBElement<GetAgentAuditReportCsv>(_GetAgentAuditReportCsv_QNAME, GetAgentAuditReportCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyWebConnectorResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyWebConnectorResponse")
    public JAXBElement<ModifyWebConnectorResponse> createModifyWebConnectorResponse(ModifyWebConnectorResponse value) {
        return new JAXBElement<ModifyWebConnectorResponse>(_ModifyWebConnectorResponse_QNAME, ModifyWebConnectorResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAgentGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getAgentGroupResponse")
    public JAXBElement<GetAgentGroupResponse> createGetAgentGroupResponse(GetAgentGroupResponse value) {
        return new JAXBElement<GetAgentGroupResponse>(_GetAgentGroupResponse_QNAME, GetAgentGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetPrompt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getPrompt")
    public JAXBElement<GetPrompt> createGetPrompt(GetPrompt value) {
        return new JAXBElement<GetPrompt>(_GetPrompt_QNAME, GetPrompt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToListCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addToListCsvResponse")
    public JAXBElement<AddToListCsvResponse> createAddToListCsvResponse(AddToListCsvResponse value) {
        return new JAXBElement<AddToListCsvResponse>(_AddToListCsvResponse_QNAME, AddToListCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ScheduleOperationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ScheduleOperationFailedFault")
    public JAXBElement<ScheduleOperationFailedException> createScheduleOperationFailedFault(ScheduleOperationFailedException value) {
        return new JAXBElement<ScheduleOperationFailedException>(_ScheduleOperationFailedFault_QNAME, ScheduleOperationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DNISIsNotAssignedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DNISIsNotAssignedFault")
    public JAXBElement<DNISIsNotAssignedException> createDNISIsNotAssignedFault(DNISIsNotAssignedException value) {
        return new JAXBElement<DNISIsNotAssignedException>(_DNISIsNotAssignedFault_QNAME, DNISIsNotAssignedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CampaignAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "CampaignAlreadyExistsFault")
    public JAXBElement<CampaignAlreadyExistsException> createCampaignAlreadyExistsFault(CampaignAlreadyExistsException value) {
        return new JAXBElement<CampaignAlreadyExistsException>(_CampaignAlreadyExistsFault_QNAME, CampaignAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IsReportRunningResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "isReportRunningResponse")
    public JAXBElement<IsReportRunningResponse> createIsReportRunningResponse(IsReportRunningResponse value) {
        return new JAXBElement<IsReportRunningResponse>(_IsReportRunningResponse_QNAME, IsReportRunningResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListImportResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getListImportResult")
    public JAXBElement<GetListImportResult> createGetListImportResult(GetListImportResult value) {
        return new JAXBElement<GetListImportResult>(_GetListImportResult_QNAME, GetListImportResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOutboundCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createOutboundCampaignResponse")
    public JAXBElement<CreateOutboundCampaignResponse> createCreateOutboundCampaignResponse(CreateOutboundCampaignResponse value) {
        return new JAXBElement<CreateOutboundCampaignResponse>(_CreateOutboundCampaignResponse_QNAME, CreateOutboundCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDNISToCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addDNISToCampaign")
    public JAXBElement<AddDNISToCampaign> createAddDNISToCampaign(AddDNISToCampaign value) {
        return new JAXBElement<AddDNISToCampaign>(_AddDNISToCampaign_QNAME, AddDNISToCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DialProfileNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DialProfileNotFoundFault")
    public JAXBElement<DialProfileNotFoundException> createDialProfileNotFoundFault(DialProfileNotFoundException value) {
        return new JAXBElement<DialProfileNotFoundException>(_DialProfileNotFoundFault_QNAME, DialProfileNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserAlreadyExistsException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UserAlreadyExistsFault")
    public JAXBElement<UserAlreadyExistsException> createUserAlreadyExistsFault(UserAlreadyExistsException value) {
        return new JAXBElement<UserAlreadyExistsException>(_UserAlreadyExistsFault_QNAME, UserAlreadyExistsException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromContactsCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromContactsCsv")
    public JAXBElement<DeleteFromContactsCsv> createDeleteFromContactsCsv(DeleteFromContactsCsv value) {
        return new JAXBElement<DeleteFromContactsCsv>(_DeleteFromContactsCsv_QNAME, DeleteFromContactsCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetCampaignDispositions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "resetCampaignDispositions")
    public JAXBElement<ResetCampaignDispositions> createResetCampaignDispositions(ResetCampaignDispositions value) {
        return new JAXBElement<ResetCampaignDispositions>(_ResetCampaignDispositions_QNAME, ResetCampaignDispositions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserInfoResponse")
    public JAXBElement<GetUserInfoResponse> createGetUserInfoResponse(GetUserInfoResponse value) {
        return new JAXBElement<GetUserInfoResponse>(_GetUserInfoResponse_QNAME, GetUserInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersGeneralInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUsersGeneralInfo")
    public JAXBElement<GetUsersGeneralInfo> createGetUsersGeneralInfo(GetUsersGeneralInfo value) {
        return new JAXBElement<GetUsersGeneralInfo>(_GetUsersGeneralInfo_QNAME, GetUsersGeneralInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DispositionNotAllowedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DispositionNotAllowedFault")
    public JAXBElement<DispositionNotAllowedException> createDispositionNotAllowedFault(DispositionNotAllowedException value) {
        return new JAXBElement<DispositionNotAllowedException>(_DispositionNotAllowedFault_QNAME, DispositionNotAllowedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PromptCantBeDeletedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "PromptCantBeDeletedFault")
    public JAXBElement<PromptCantBeDeletedException> createPromptCantBeDeletedFault(PromptCantBeDeletedException value) {
        return new JAXBElement<PromptCantBeDeletedException>(_PromptCantBeDeletedFault_QNAME, PromptCantBeDeletedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetListPositionResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "resetListPositionResponse")
    public JAXBElement<ResetListPositionResponse> createResetListPositionResponse(ResetListPositionResponse value) {
        return new JAXBElement<ResetListPositionResponse>(_ResetListPositionResponse_QNAME, ResetListPositionResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UnknownIdentifierException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "UnknownIdentifierFault")
    public JAXBElement<UnknownIdentifierException> createUnknownIdentifierFault(UnknownIdentifierException value) {
        return new JAXBElement<UnknownIdentifierException>(_UnknownIdentifierFault_QNAME, UnknownIdentifierException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteCallVariableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteCallVariableResponse")
    public JAXBElement<DeleteCallVariableResponse> createDeleteCallVariableResponse(DeleteCallVariableResponse value) {
        return new JAXBElement<DeleteCallVariableResponse>(_DeleteCallVariableResponse_QNAME, DeleteCallVariableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteReasonCodeByTypeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteReasonCodeByTypeResponse")
    public JAXBElement<DeleteReasonCodeByTypeResponse> createDeleteReasonCodeByTypeResponse(DeleteReasonCodeByTypeResponse value) {
        return new JAXBElement<DeleteReasonCodeByTypeResponse>(_DeleteReasonCodeByTypeResponse_QNAME, DeleteReasonCodeByTypeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DispositionNotFoundException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "DispositionNotFoundFault")
    public JAXBElement<DispositionNotFoundException> createDispositionNotFoundFault(DispositionNotFoundException value) {
        return new JAXBElement<DispositionNotFoundException>(_DispositionNotFoundFault_QNAME, DispositionNotFoundException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateDispositionsCsvResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateDispositionsCsvResponse")
    public JAXBElement<UpdateDispositionsCsvResponse> createUpdateDispositionsCsvResponse(UpdateDispositionsCsvResponse value) {
        return new JAXBElement<UpdateDispositionsCsvResponse>(_UpdateDispositionsCsvResponse_QNAME, UpdateDispositionsCsvResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TtsGenerationFailedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "TtsGenerationFailed")
    public JAXBElement<TtsGenerationFailedException> createTtsGenerationFailed(TtsGenerationFailedException value) {
        return new JAXBElement<TtsGenerationFailedException>(_TtsGenerationFailed_QNAME, TtsGenerationFailedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AdminSessionClosedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "AdminSessionClosedFault")
    public JAXBElement<AdminSessionClosedException> createAdminSessionClosedFault(AdminSessionClosedException value) {
        return new JAXBElement<AdminSessionClosedException>(_AdminSessionClosedFault_QNAME, AdminSessionClosedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RunReportResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "runReportResponse")
    public JAXBElement<RunReportResponse> createRunReportResponse(RunReportResponse value) {
        return new JAXBElement<RunReportResponse>(_RunReportResponse_QNAME, RunReportResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddToListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addToListResponse")
    public JAXBElement<AddToListResponse> createAddToListResponse(AddToListResponse value) {
        return new JAXBElement<AddToListResponse>(_AddToListResponse_QNAME, AddToListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConcurrentModificationException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "ConcurrentModificationFault")
    public JAXBElement<ConcurrentModificationException> createConcurrentModificationFault(ConcurrentModificationException value) {
        return new JAXBElement<ConcurrentModificationException>(_ConcurrentModificationFault_QNAME, ConcurrentModificationException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperationsLimitExceededException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "OperationsLimitExceededFault")
    public JAXBElement<OperationsLimitExceededException> createOperationsLimitExceededFault(OperationsLimitExceededException value) {
        return new JAXBElement<OperationsLimitExceededException>(_OperationsLimitExceededFault_QNAME, OperationsLimitExceededException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserProfileUserListResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserProfileUserListResponse")
    public JAXBElement<ModifyUserProfileUserListResponse> createModifyUserProfileUserListResponse(ModifyUserProfileUserListResponse value) {
        return new JAXBElement<ModifyUserProfileUserListResponse>(_ModifyUserProfileUserListResponse_QNAME, ModifyUserProfileUserListResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetWebConnectorsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getWebConnectorsResponse")
    public JAXBElement<GetWebConnectorsResponse> createGetWebConnectorsResponse(GetWebConnectorsResponse value) {
        return new JAXBElement<GetWebConnectorsResponse>(_GetWebConnectorsResponse_QNAME, GetWebConnectorsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WrongCampaignStateException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "WrongCampaignStateFault")
    public JAXBElement<WrongCampaignStateException> createWrongCampaignStateFault(WrongCampaignStateException value) {
        return new JAXBElement<WrongCampaignStateException>(_WrongCampaignStateFault_QNAME, WrongCampaignStateException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AsyncUpdateCrmRecords }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "asyncUpdateCrmRecords")
    public JAXBElement<AsyncUpdateCrmRecords> createAsyncUpdateCrmRecords(AsyncUpdateCrmRecords value) {
        return new JAXBElement<AsyncUpdateCrmRecords>(_AsyncUpdateCrmRecords_QNAME, AsyncUpdateCrmRecords.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link NICELicencesExceededException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "NICELicencesExceededFault")
    public JAXBElement<NICELicencesExceededException> createNICELicencesExceededFault(NICELicencesExceededException value) {
        return new JAXBElement<NICELicencesExceededException>(_NICELicencesExceededFault_QNAME, NICELicencesExceededException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SetSkillVoicemailGreeting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "setSkillVoicemailGreeting")
    public JAXBElement<SetSkillVoicemailGreeting> createSetSkillVoicemailGreeting(SetSkillVoicemailGreeting value) {
        return new JAXBElement<SetSkillVoicemailGreeting>(_SetSkillVoicemailGreeting_QNAME, SetSkillVoicemailGreeting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillVoicemailGreeting }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillVoicemailGreeting")
    public JAXBElement<GetSkillVoicemailGreeting> createGetSkillVoicemailGreeting(GetSkillVoicemailGreeting value) {
        return new JAXBElement<GetSkillVoicemailGreeting>(_GetSkillVoicemailGreeting_QNAME, GetSkillVoicemailGreeting.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCampaignProfileCrmCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCampaignProfileCrmCriteria")
    public JAXBElement<ModifyCampaignProfileCrmCriteria> createModifyCampaignProfileCrmCriteria(ModifyCampaignProfileCrmCriteria value) {
        return new JAXBElement<ModifyCampaignProfileCrmCriteria>(_ModifyCampaignProfileCrmCriteria_QNAME, ModifyCampaignProfileCrmCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyCallVariablesGroup }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyCallVariablesGroup")
    public JAXBElement<ModifyCallVariablesGroup> createModifyCallVariablesGroup(ModifyCallVariablesGroup value) {
        return new JAXBElement<ModifyCallVariablesGroup>(_ModifyCallVariablesGroup_QNAME, ModifyCallVariablesGroup.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateIVRScript }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createIVRScript")
    public JAXBElement<CreateIVRScript> createCreateIVRScript(CreateIVRScript value) {
        return new JAXBElement<CreateIVRScript>(_CreateIVRScript_QNAME, CreateIVRScript.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteAgentGroupResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteAgentGroupResponse")
    public JAXBElement<DeleteAgentGroupResponse> createDeleteAgentGroupResponse(DeleteAgentGroupResponse value) {
        return new JAXBElement<DeleteAgentGroupResponse>(_DeleteAgentGroupResponse_QNAME, DeleteAgentGroupResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetReportResult }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getReportResult")
    public JAXBElement<GetReportResult> createGetReportResult(GetReportResult value) {
        return new JAXBElement<GetReportResult>(_GetReportResult_QNAME, GetReportResult.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ForceStopCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "forceStopCampaign")
    public JAXBElement<ForceStopCampaign> createForceStopCampaign(ForceStopCampaign value) {
        return new JAXBElement<ForceStopCampaign>(_ForceStopCampaign_QNAME, ForceStopCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RenameCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "renameCampaign")
    public JAXBElement<RenameCampaign> createRenameCampaign(RenameCampaign value) {
        return new JAXBElement<RenameCampaign>(_RenameCampaign_QNAME, RenameCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUsersInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUsersInfoResponse")
    public JAXBElement<GetUsersInfoResponse> createGetUsersInfoResponse(GetUsersInfoResponse value) {
        return new JAXBElement<GetUsersInfoResponse>(_GetUsersInfoResponse_QNAME, GetUsersInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyOutboundCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyOutboundCampaign")
    public JAXBElement<ModifyOutboundCampaign> createModifyOutboundCampaign(ModifyOutboundCampaign value) {
        return new JAXBElement<ModifyOutboundCampaign>(_ModifyOutboundCampaign_QNAME, ModifyOutboundCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link WrongCampaignTypeException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "WrongCampaignTypeFault")
    public JAXBElement<WrongCampaignTypeException> createWrongCampaignTypeFault(WrongCampaignTypeException value) {
        return new JAXBElement<WrongCampaignTypeException>(_WrongCampaignTypeFault_QNAME, WrongCampaignTypeException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteIVRScript }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteIVRScript")
    public JAXBElement<DeleteIVRScript> createDeleteIVRScript(DeleteIVRScript value) {
        return new JAXBElement<DeleteIVRScript>(_DeleteIVRScript_QNAME, DeleteIVRScript.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromList }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromList")
    public JAXBElement<DeleteFromList> createDeleteFromList(DeleteFromList value) {
        return new JAXBElement<DeleteFromList>(_DeleteFromList_QNAME, DeleteFromList.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteFromContactsFtp }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteFromContactsFtp")
    public JAXBElement<DeleteFromContactsFtp> createDeleteFromContactsFtp(DeleteFromContactsFtp value) {
        return new JAXBElement<DeleteFromContactsFtp>(_DeleteFromContactsFtp_QNAME, DeleteFromContactsFtp.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TooManyUsersException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "TooManyUsersFault")
    public JAXBElement<TooManyUsersException> createTooManyUsersFault(TooManyUsersException value) {
        return new JAXBElement<TooManyUsersException>(_TooManyUsersFault_QNAME, TooManyUsersException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPromptTTS }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addPromptTTS")
    public JAXBElement<AddPromptTTS> createAddPromptTTS(AddPromptTTS value) {
        return new JAXBElement<AddPromptTTS>(_AddPromptTTS_QNAME, AddPromptTTS.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateContacts }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "updateContacts")
    public JAXBElement<UpdateContacts> createUpdateContacts(UpdateContacts value) {
        return new JAXBElement<UpdateContacts>(_UpdateContacts_QNAME, UpdateContacts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckDncForNumbers }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "checkDncForNumbers")
    public JAXBElement<CheckDncForNumbers> createCheckDncForNumbers(CheckDncForNumbers value) {
        return new JAXBElement<CheckDncForNumbers>(_CheckDncForNumbers_QNAME, CheckDncForNumbers.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDispositions }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDispositions")
    public JAXBElement<GetDispositions> createGetDispositions(GetDispositions value) {
        return new JAXBElement<GetDispositions>(_GetDispositions_QNAME, GetDispositions.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyUserProfileSkillsResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyUserProfileSkillsResponse")
    public JAXBElement<ModifyUserProfileSkillsResponse> createModifyUserProfileSkillsResponse(ModifyUserProfileSkillsResponse value) {
        return new JAXBElement<ModifyUserProfileSkillsResponse>(_ModifyUserProfileSkillsResponse_QNAME, ModifyUserProfileSkillsResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddPromptWav }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addPromptWav")
    public JAXBElement<AddPromptWav> createAddPromptWav(AddPromptWav value) {
        return new JAXBElement<AddPromptWav>(_AddPromptWav_QNAME, AddPromptWav.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetListsForCampaign }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getListsForCampaign")
    public JAXBElement<GetListsForCampaign> createGetListsForCampaign(GetListsForCampaign value) {
        return new JAXBElement<GetListsForCampaign>(_GetListsForCampaign_QNAME, GetListsForCampaign.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkillsInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkillsInfo")
    public JAXBElement<GetSkillsInfo> createGetSkillsInfo(GetSkillsInfo value) {
        return new JAXBElement<GetSkillsInfo>(_GetSkillsInfo_QNAME, GetSkillsInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteSkillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteSkillResponse")
    public JAXBElement<DeleteSkillResponse> createDeleteSkillResponse(DeleteSkillResponse value) {
        return new JAXBElement<DeleteSkillResponse>(_DeleteSkillResponse_QNAME, DeleteSkillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeletePrompt }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deletePrompt")
    public JAXBElement<DeletePrompt> createDeletePrompt(DeletePrompt value) {
        return new JAXBElement<DeletePrompt>(_DeletePrompt_QNAME, DeletePrompt.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetCallLogReportCsv }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getCallLogReportCsv")
    public JAXBElement<GetCallLogReportCsv> createGetCallLogReportCsv(GetCallLogReportCsv value) {
        return new JAXBElement<GetCallLogReportCsv>(_GetCallLogReportCsv_QNAME, GetCallLogReportCsv.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateCampaignProfileResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createCampaignProfileResponse")
    public JAXBElement<CreateCampaignProfileResponse> createCreateCampaignProfileResponse(CreateCampaignProfileResponse value) {
        return new JAXBElement<CreateCampaignProfileResponse>(_CreateCampaignProfileResponse_QNAME, CreateCampaignProfileResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteReasonCodeByType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteReasonCodeByType")
    public JAXBElement<DeleteReasonCodeByType> createDeleteReasonCodeByType(DeleteReasonCodeByType value) {
        return new JAXBElement<DeleteReasonCodeByType>(_DeleteReasonCodeByType_QNAME, DeleteReasonCodeByType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDisposition }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeDisposition")
    public JAXBElement<RemoveDisposition> createRemoveDisposition(RemoveDisposition value) {
        return new JAXBElement<RemoveDisposition>(_RemoveDisposition_QNAME, RemoveDisposition.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SkillCantBeDeletedException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "SkillCantBeDeletedFault")
    public JAXBElement<SkillCantBeDeletedException> createSkillCantBeDeletedFault(SkillCantBeDeletedException value) {
        return new JAXBElement<SkillCantBeDeletedException>(_SkillCantBeDeletedFault_QNAME, SkillCantBeDeletedException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetSkill }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getSkill")
    public JAXBElement<GetSkill> createGetSkill(GetSkill value) {
        return new JAXBElement<GetSkill>(_GetSkill_QNAME, GetSkill.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyVCCConfigurationResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyVCCConfigurationResponse")
    public JAXBElement<ModifyVCCConfigurationResponse> createModifyVCCConfigurationResponse(ModifyVCCConfigurationResponse value) {
        return new JAXBElement<ModifyVCCConfigurationResponse>(_ModifyVCCConfigurationResponse_QNAME, ModifyVCCConfigurationResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifySkillResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifySkillResponse")
    public JAXBElement<ModifySkillResponse> createModifySkillResponse(ModifySkillResponse value) {
        return new JAXBElement<ModifySkillResponse>(_ModifySkillResponse_QNAME, ModifySkillResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyReasonCodeResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "modifyReasonCodeResponse")
    public JAXBElement<ModifyReasonCodeResponse> createModifyReasonCodeResponse(ModifyReasonCodeResponse value) {
        return new JAXBElement<ModifyReasonCodeResponse>(_ModifyReasonCodeResponse_QNAME, ModifyReasonCodeResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RemoveDispositionsFromCampaignResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "removeDispositionsFromCampaignResponse")
    public JAXBElement<RemoveDispositionsFromCampaignResponse> createRemoveDispositionsFromCampaignResponse(RemoveDispositionsFromCampaignResponse value) {
        return new JAXBElement<RemoveDispositionsFromCampaignResponse>(_RemoveDispositionsFromCampaignResponse_QNAME, RemoveDispositionsFromCampaignResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserSkillModifyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "userSkillModifyResponse")
    public JAXBElement<UserSkillModifyResponse> createUserSkillModifyResponse(UserSkillModifyResponse value) {
        return new JAXBElement<UserSkillModifyResponse>(_UserSkillModifyResponse_QNAME, UserSkillModifyResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDispositionsImportResultResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getDispositionsImportResultResponse")
    public JAXBElement<GetDispositionsImportResultResponse> createGetDispositionsImportResultResponse(GetDispositionsImportResultResponse value) {
        return new JAXBElement<GetDispositionsImportResultResponse>(_GetDispositionsImportResultResponse_QNAME, GetDispositionsImportResultResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddNumbersToDnc }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "addNumbersToDnc")
    public JAXBElement<AddNumbersToDnc> createAddNumbersToDnc(AddNumbersToDnc value) {
        return new JAXBElement<AddNumbersToDnc>(_AddNumbersToDnc_QNAME, AddNumbersToDnc.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "createUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "deleteUser")
    public JAXBElement<DeleteUser> createDeleteUser(DeleteUser value) {
        return new JAXBElement<DeleteUser>(_DeleteUser_QNAME, DeleteUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserProfiles }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.admin.ws.five9.com/v2/", name = "getUserProfiles")
    public JAXBElement<GetUserProfiles> createGetUserProfiles(GetUserProfiles value) {
        return new JAXBElement<GetUserProfiles>(_GetUserProfiles_QNAME, GetUserProfiles.class, null, value);
    }

}
