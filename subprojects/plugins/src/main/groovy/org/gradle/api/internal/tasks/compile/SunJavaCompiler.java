/*
 * Copyright 2011 the original author or authors.
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
package org.gradle.api.internal.tasks.compile;

import com.sun.tools.javac.Main;
import org.gradle.api.tasks.WorkResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public class SunJavaCompiler extends CommandLineJavaCompilerSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(SunJavaCompiler.class);

    public WorkResult execute(JavaCompileSpec spec) {
        LOGGER.info("Compiling using Sun Java Compiler API.");
        listFilesIfRequested(spec);

        List<String> options = generateCommandLineOptions(spec);
        for (File file : spec.getSource()) {
            options.add(file.getPath());
        }

        int exitCode = Main.compile(options.toArray(new String[options.size()]));
        if (exitCode != 0 && spec.getCompileOptions().isFailOnError()) {
            throw new CompilationFailedException();
        }

        return new SimpleWorkResult(true);
    }
}
