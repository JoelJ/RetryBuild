package com.attask.jenkins;

import hudson.model.*;
import hudson.security.Permission;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * User: Joel Johnson
 * Date: 6/29/12
 * Time: 9:43 PM
 */
public class RebuildAction implements Action {
	private final String buildId;

	public RebuildAction(AbstractBuild build) {
		this.buildId = build == null ? null : build.getExternalizableId();
	}

	public String getBuildId() {
		return buildId;
	}

	public AbstractBuild findBuild() {
		if(buildId == null) {
			return null;
		}
		return (AbstractBuild)Run.fromExternalizableId(buildId);
	}

	public List<ParameterValue> findParameters() {
		AbstractBuild run = findBuild();
		ParametersAction action = run.getAction(ParametersAction.class);
		if(action != null) {
			return action.getParameters();
		}
		return Collections.emptyList();
	}

	public void doRebuild(StaplerRequest request, StaplerResponse response) throws IOException {
		AbstractBuild run = findBuild();

		ParametersAction action = run.getAction(ParametersAction.class);
		if(action != null) {
			User currentUser = User.current();
			String fullName;
			if(currentUser == null) {
				fullName = "anonymous";
			} else {
				fullName = currentUser.getFullName();
			}
			run.getProject().scheduleBuild(0, new RebuildCause(fullName, run), action);
		}
		response.sendRedirect("../..");
	}

	public String getIconFileName() {
		return "clock.png";
	}

	public String getDisplayName() {
		return "Rebuild";
	}

	public String getUrlName() {
		return "rebuild";
	}
}
