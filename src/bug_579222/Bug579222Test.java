package bug_579222;

import org.eclipse.core.resources.ICommand;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.jdt.core.IAccessRule;
import org.eclipse.jdt.core.IClasspathAttribute;
import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.junit.Test;

public class Bug579222Test {

	@Test
	public void testIt() throws CoreException {
		IProject project = ResourcesPlugin.getWorkspace().getRoot().getProject("demo");
		IProjectDescription projectDescription = createProjectDescription();
		project.create(projectDescription, null);
		project.open(null);
		IJavaProject javaProject = JavaCore.create(project);

		IClasspathEntry classpathEntry = JavaCore.newContainerEntry(
				new Path("org.eclipse.jdt.junit.JUNIT_CONTAINER").append("4"), new IAccessRule[0],
				new IClasspathAttribute[] { JavaCore.newClasspathAttribute("test", "true") }, false/* not exported */);

		javaProject.setRawClasspath(new IClasspathEntry[] { classpathEntry }, null);

		javaProject.setRawClasspath(javaProject.getRawClasspath(), null);
	}

	protected IProjectDescription createProjectDescription() {
		final IProjectDescription projectDescription = ResourcesPlugin.getWorkspace().newProjectDescription("demo");
		ICommand command = projectDescription.newCommand();
		command.setBuilderName(JavaCore.BUILDER_ID);
		projectDescription.setBuildSpec(new ICommand[] {
				command
		});
		projectDescription.setNatureIds(new String[] {JavaCore.NATURE_ID});
		return projectDescription;
	}

}
