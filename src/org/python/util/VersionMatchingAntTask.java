package org.python.util;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Location;
import org.apache.tools.ant.Task;

public class VersionMatchingAntTask extends Task {

    @Override
    public void execute() throws BuildException {
        Location location = getLocation();
        log("location = " + location);
        throw new BuildException("Versions to not match");
    }

}
