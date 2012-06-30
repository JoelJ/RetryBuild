package com.attask.jenkins;

import hudson.console.HyperlinkNote;
import hudson.model.Cause;
import hudson.model.Messages;
import hudson.model.Run;
import hudson.model.TaskListener;

/**
 * User: Joel Johnson
 * Date: 6/29/12
 * Time: 10:05 PM
 */
public class RebuildCause extends Cause {
	private final String username;
	private final String rebuiltJobName;
	private final String url;

	public RebuildCause(String username, Run rebuiltJob) {
		this.username = username;
		this.rebuiltJobName = rebuiltJob.getFullDisplayName();
		this.url = rebuiltJob.getUrl();
	}

	public String getRebuiltJobName() {
		return rebuiltJobName;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	@Override
	public String getShortDescription() {
		return username + " requested a rebuild of " + rebuiltJobName;
	}

	@Override
	public void print(TaskListener listener) {
		listener.getLogger().println(
				username + " requested a rebuild of "+HyperlinkNote.encodeTo("../../../"+url, rebuiltJobName)
		);
	}
}
