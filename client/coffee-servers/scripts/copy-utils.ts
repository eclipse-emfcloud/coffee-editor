/********************************************************************************
 * Copyright (c) 2022 EclipseSource and others.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0, or the MIT License which is
 * available at https://opensource.org/licenses/MIT.
 *
 * SPDX-License-Identifier: EPL-2.0 OR MIT
 ********************************************************************************/
import { copyFileSync, existsSync, mkdirSync } from 'fs';
import { copySync } from 'fs-extra';
import { platform } from 'os';
import { join } from 'path';
import { exit } from 'process';

export function log(logMsg: string): void {
    const now = new Date(Date.now());
    console.log(now.toISOString() + ' | ' + logMsg);
}

export function logError(logMsg: string): void {
    const now = new Date(Date.now());
    console.error(now.toISOString() + ' | ' + logMsg);
}

export function getOSProductPath(): string {
    const osType = platform();
    let productPath = '';

    log('Determining OS...');
    if (osType === 'linux') {
        productPath = join('linux', 'gtk');
        log('Running on Linux');
    } else if (osType === 'darwin') {
        productPath = join('macosx', 'cocoa');
        log('Running on MacOS');
    } else if (osType === 'cygwin') {
        // POSIX compatibility layer and Linux environment emulation for Windows
        productPath = join('win32', 'win32');
        log('Running on Windows with Cygwin');
    } else if (osType === 'win32') {
        productPath = join('win32', 'win32');
        log('Running on Windows');
    }
    return productPath;
}

function prepareTargetDir(targetPath: string): void {
    // Check if target directory exists, create otherwise
    if (existsSync(targetPath)) {
        log('Target directory exists!');
    } else {
        try {
            log('Creating target directory...');
            mkdirSync(targetPath, { recursive: true });
            log(`Target directory '${targetPath}' was created successfully!`);
        } catch (err) {
            if (err instanceof Error) {
                logError(err.message);
            }
        }
    }
}

function checkSourcePath(sourcePath: string): void {
    if (!existsSync(sourcePath)) {
        logError(`Error: Source path '${sourcePath}' does not exist!`);
        exit(1);
    }
    log('Source directory exists!');
}

export function copyBackendDirectory(sourcePath: string, targetPath: string): void {
    // Check source directory
    checkSourcePath(sourcePath);
    // Check and prepare target directory
    prepareTargetDir(targetPath);
    // Start copying
    copySync(sourcePath, targetPath, { recursive: true, overwrite: false });
    log(`Copy to '${targetPath} was successful!`);
}

export function copyBackendFile(sourcePath: string, targetPath: string, jarName: string): void {
    // Check source file
    checkSourcePath(sourcePath);
    // Check and prepare target directory
    prepareTargetDir(targetPath);
    // Start copying
    copyFileSync(sourcePath, join(targetPath, jarName));
    log(`Copy to '${targetPath} was successful!`);
}
