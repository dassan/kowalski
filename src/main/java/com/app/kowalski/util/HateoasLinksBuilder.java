package com.app.kowalski.util;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;
import java.util.Set;

import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;

import com.app.kowalski.activity.ActivityController;
import com.app.kowalski.activity.ActivityDTO;
import com.app.kowalski.project.ProjectController;
import com.app.kowalski.project.ProjectDTO;
import com.app.kowalski.task.TaskController;
import com.app.kowalski.task.TaskDTO;
import com.app.kowalski.user.KowalskiUserController;
import com.app.kowalski.user.KowalskiUserDTO;

public class HateoasLinksBuilder {

	public static void createHateoasForProject(ProjectDTO projectDTO) {
		Link selfLink = linkTo(ProjectController.class).slash(projectDTO.getProjectId()).withSelfRel();
		projectDTO.add(selfLink);

		ResponseEntity<List<ActivityDTO>> methodLinkBuilder = methodOn(ProjectController.class)
				.getActivitiesForProject(projectDTO.getProjectId());
		Link activitiesLink = linkTo(methodLinkBuilder).withRel("activities");
		projectDTO.add(activitiesLink);

		ResponseEntity<KowalskiUserDTO> accountableLinkBuilder = methodOn(ProjectController.class)
				.getAccountableForProject(projectDTO.getProjectId());
		Link accountableLink = linkTo(accountableLinkBuilder).withRel("accountable");
		projectDTO.add(accountableLink);

		ResponseEntity<Set<KowalskiUserDTO>> membersLinkBuilder = methodOn(ProjectController.class)
				.getProjectMembers(projectDTO.getProjectId());
		Link membersLink = linkTo(membersLinkBuilder).withRel("members");
		projectDTO.add(membersLink);
	}

	public static void createHateoasForActivity(ActivityDTO activityDTO) {
		Integer activityDTOId = activityDTO.getActivityId();

		Link selfLink = linkTo(ActivityController.class).slash(activityDTOId).withSelfRel();
		activityDTO.add(selfLink);

		Link projectLink = linkTo(ProjectController.class).slash(activityDTO.getProjectId()).withRel("project");
		activityDTO.add(projectLink);

		ResponseEntity<List<TaskDTO>> tasksLinkBuilder = methodOn(ActivityController.class)
				.getTasksForActivity(activityDTO.getActivityId());
		Link tasksLink = linkTo(tasksLinkBuilder).withRel("tasks");
		activityDTO.add(tasksLink);

		ResponseEntity<KowalskiUserDTO> accountableLinkBuilder = methodOn(ActivityController.class)
				.getAccountableForActivity(activityDTO.getActivityId());
		Link accountableLink = linkTo(accountableLinkBuilder).withRel("accountable");
		activityDTO.add(accountableLink);
	}

	public static void createHateoasForTask(TaskDTO taskDTO) {
		Integer taskDTOId = taskDTO.getTaskId();

		Link selfLink = linkTo(TaskController.class).slash(taskDTOId).withSelfRel();
		taskDTO.add(selfLink);

		Link activityLink = linkTo(ActivityController.class).slash(taskDTO.getActivityId()).withRel("activity");
		taskDTO.add(activityLink);

		ResponseEntity<KowalskiUserDTO> accountableLinkBuilder = methodOn(TaskController.class)
				.getAccountableForTask(taskDTO.getTaskId());
		Link accountableLink = linkTo(accountableLinkBuilder).withRel("accountable");
		taskDTO.add(accountableLink);
	}

	public static void createHateoasForKowalskiUser(KowalskiUserDTO kowalskiUserDTO) {
		Integer kUserId = kowalskiUserDTO.getkUserId();

		Link selfLink = linkTo(KowalskiUserController.class).slash(kUserId).withSelfRel();
		kowalskiUserDTO.add(selfLink);

		ResponseEntity<List<ProjectDTO>> accountableProjectsLinkBuilder = methodOn(KowalskiUserController.class)
				.getAccountableProjects(kUserId);
		Link accountableProjectsLink = linkTo(accountableProjectsLinkBuilder).withRel("accountableProjects");
		kowalskiUserDTO.add(accountableProjectsLink);

		ResponseEntity<List<ActivityDTO>> accountableActivitiesLinkBuilder = methodOn(KowalskiUserController.class)
				.getAccountableActivities(kUserId);
		Link accountableActivitiesLink = linkTo(accountableActivitiesLinkBuilder).withRel("accountableActivities");
		kowalskiUserDTO.add(accountableActivitiesLink);

		ResponseEntity<List<TaskDTO>> accountableTasksLinkBuilder = methodOn(KowalskiUserController.class)
				.getAccountableTasks(kUserId);
		Link accountableTasksLink = linkTo(accountableTasksLinkBuilder).withRel("accountableTasks");
		kowalskiUserDTO.add(accountableTasksLink);

		ResponseEntity<Set<ProjectDTO>> projectsLinkBuilder = methodOn(KowalskiUserController.class).getProjects(kUserId);
		Link projectsLink = linkTo(projectsLinkBuilder).withRel("projects");
		kowalskiUserDTO.add(projectsLink);
	}
}
