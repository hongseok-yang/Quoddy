package org.fogbeam.quoddy

import org.apache.shiro.SecurityUtils
import org.fogbeam.quoddy.controller.mixins.SidebarPopulatorMixin
import org.fogbeam.quoddy.stream.ActivityStreamItem

@Mixin(SidebarPopulatorMixin)
class HomeController {

	def userService;
	def eventStreamService;
	def userStreamDefinitionService;
	def userListService;
	def userGroupService;
	def businessEventSubscriptionService;
	def calendarFeedSubscriptionService;
	def activitiUserTaskSubscriptionService;
	def rssFeedSubscriptionService;
		
    def index = {
    		
    	def userId = params.userId;
    	User user = null;
		List<ActivityStreamItem> activities = new ArrayList<ActivityStreamItem>();
		
		if( userId != null )
    	{
			println "getting User by userId: ${userId}";
    		user = userService.findUserByUserId( userId );
    	}
    	else
    	{
			println "Looking up User in session";
			user = SecurityUtils.subject.principal;
    		if( user != null )
    		{
				println "Found User in Session";
    			user = userService.findUserByUserId( user.userId );
    		}
			else
			{
				println "No user in Session";
			}
    	}
		
		Map model = [:];
		if( user )
		{
			// TODO: this should take the selected UserStream into account when
			// determining what activities to include in the activities list
			UserStreamDefinition selectedStream = null;
			if( params.streamId )
			{
				Long streamId = Long.valueOf( params.streamId );
				selectedStream = userStreamDefinitionService.findStreamById( streamId );
			}
			else 
			{
				selectedStream = userStreamDefinitionService.getStreamForUser( user, UserStreamDefinition.DEFAULT_STREAM );	
			}
			
			
			activities = eventStreamService.getRecentActivitiesForUser( user, 25, selectedStream );
			model.putAll( [user:user, activities:activities, streamId:params.streamId] );
			
			Map sidebarCollections = populateSidebarCollections( this, user );
			model.putAll( sidebarCollections );
			
		}	
		
		return model;
    }
}
