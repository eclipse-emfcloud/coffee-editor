#!/bin/bash
SNAPSHOT_REPOSITORY=https://oss.sonatype.org/content/repositories/snapshots
##############################################################################################################################################
# Helper functions
##############################################################################################################################################
# Resolves the concrete qualified version for a snaphot m2 artifact
# Parameters
#   $1 -> groupId
#   $2 -> artifactId
#   $3 -> version
function resolve_version(){
    _groupPath=${1//./\/}
    _versionDir="$TEMP_DIR"/"$1"/"$2"
    _metaData=maven-metadata.xml
    if [ ! -d $_versionDir ]
    then
        mkdir -p $_versionDir
    fi
    
    cd $_versionDir || exit
    
    if [ ! -f $_metaData ]
    then
        #Download corresponding metadata from snapshot repository
        curl -s "$SNAPSHOT_REPOSITORY"/"$_groupPath"/"$2"/"$3"/$_metaData > $_metaData
    fi
    # Parse timestampt of latest snapshot
    _timestamp=$(xmllint --xpath 'string(/metadata/versioning/snapshot/timestamp)' maven-metadata.xml)
    # Parse buildnummer of latest snapshot
    _buildNummer=$(xmllint --xpath 'string(/metadata/versioning/snapshot/buildNumber)' maven-metadata.xml)
    echo "${3//-SNAPSHOT}"-"${_timestamp}"-"${_buildNummer}"
}

# Download latest snapshot p2 artifact
# Parameters
#   $1 -> groupdId
#   $2 -> artifactId
#   $3 -> version
#   $4 -> optional qualifier


function download_artifact() {
    _version=$(resolve_version "$1" "$2" "$3")
    _groupPath=${1//./\/}
    _versionDir="$TEMP_DIR"/"$1"/"$2"
    
    if [ -z "$_version" ]
    then
        echo "ERROR - Could not resolve version info for "$1" "$2" "$3""
        exit 1
    fi
    cd $_versionDir || exit
    _baseURL="$SNAPSHOT_REPOSITORY"/"$_groupPath"/"$2"/"$3"
    _jarfile=${2}-"${_version}-${4}".jar
    
    
    
    wget -q "$_baseURL"/"$_jarfile" #Download jarfile
    
}

# Copy latest snapshot p2 artifact
# Parameters
#   $1 -> groupdId
#   $2 -> artifactId
#   $3 -> version
#   $4 -> destination
#   $5 -> (optional) --sources Flag. if set the corresponding sources should be downloaded as well

function copy_artifact() {
    _version=$(resolve_version "$1" "$2" "$3")
    _versionDir="$TEMP_DIR"/"$1"/"$2"
    _jarfile=${2}-"${_version}".jar
    
    cd $_versionDir || exit
    if  [ ! -f $_jarfile ]
    then
        download_artifact $1 $2 $3 $5
    fi
    
    cp $_jarfile ${4}/$2-$3-$5.jar
}
