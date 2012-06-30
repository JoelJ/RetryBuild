package com.attask.jenkins;

import hudson.Extension;
import hudson.XmlFile;
import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.model.Saveable;
import hudson.model.listeners.SaveableListener;

import java.io.IOException;

/**
 * User: Joel Johnson
 * Date: 6/29/12
 * Time: 9:42 PM
 */
@Extension
public class SaveListener extends SaveableListener {
	public void onChange(Saveable o, XmlFile file) {
		if(o instanceof AbstractBuild) {
			AbstractBuild run = (AbstractBuild)o;
			if(run.getAction(RebuildAction.class) == null) {
				run.addAction(new RebuildAction(run));
				try {
					run.save();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
