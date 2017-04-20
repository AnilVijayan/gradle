/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.gradle.plugins.ide.internal.configurer;

import org.gradle.api.Project;
import org.gradle.api.internal.project.ProjectIdentifier;
import org.gradle.api.internal.project.ProjectInternal;
import org.gradle.initialization.BuildProjectRegistry;

import java.util.Map;

public class DefaultUniqueProjectNameProvider implements UniqueProjectNameProvider {
    private final BuildProjectRegistry projectRegistry;

    public DefaultUniqueProjectNameProvider(BuildProjectRegistry projectRegistry) {
        this.projectRegistry = projectRegistry;
    }

    // TODO:DAZ Avoid duplicating the de-duplication work for every project.
    @Override
    public String getUniqueName(Project project) {
        HierarchicalElementDeduplicator<ProjectIdentifier> deduplicator = new HierarchicalElementDeduplicator<ProjectIdentifier>(new ProjectDeduplicationAdapter());
        Map<ProjectIdentifier, String> deduplicated = deduplicator.deduplicate(projectRegistry.getAllProjects());

        // TODO:DAZ Could be more efficient when matching
        for (ProjectIdentifier projectIdentifier : deduplicated.keySet()) {
            if (equals(projectIdentifier, (ProjectInternal) project)) {
                return deduplicated.get(projectIdentifier);
            }
        }
        return project.getName();
    }

    private boolean equals(ProjectIdentifier one, ProjectInternal two) {
        if (one == null && two == null) {
            return true;
        }
        if (one == null || two == null) {
            return false;
        }
        return one.getName().equals(two.getName())
            && one.getPath().equals(two.getPath())
            && equals(one.getParentIdentifier(), two.getParent());
    }

    // TODO:DAZ Simplify now that there are not multiple deduplicator types
    private static class ProjectDeduplicationAdapter implements NameDeduplicationAdapter<ProjectIdentifier> {
        @Override
        public String getName(ProjectIdentifier element) {
            return element.getName();
        }

        @Override
        public ProjectIdentifier getParent(ProjectIdentifier element) {
            return element.getParentIdentifier();
        }
    }
}
