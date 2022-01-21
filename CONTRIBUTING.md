# Contributing to Eclipse EMF.cloud: Coffee Editor

Thank you for your interest in the Coffee Editor project!
The following is a set of guidelines for contributing to the Coffee Editor.

## Code of Conduct

This project is governed by the [Eclipse Community Code of Conduct](CODE_OF_CONDUCT.md).
By participating, you are expected to uphold this code.

## Communication

The following communication channels are available:

* [GitHub issues](https://github.com/eclipse-emfcloud/coffee-editor/issues) - for bug reports, feature requests, etc.
* [GitHub Discussions](https://github.com/eclipse-emfcloud/emfcloud/discussions) - for questions
* [Developer mailing list](https://accounts.eclipse.org/mailing-list/emfcloud-dev) - for organizational issues (e.g. elections of new committers)

In case you have a question, please look into the [GitHub Discussions](https://github.com/eclipse-emfcloud/emfcloud/discussions) first.
If you don't find any answer there, feel free to start a new discussion or create a new [issue](https://github.com/eclipse-emfcloud/coffee-editor/issues) to get help.

## How to Contribute

In order to contribute, please first open an issue in this project.
This issue should describe the bug you intend to fix or the feature you would like to add.
Once you have your code ready for review, please open a pull request in the respective repository.
A [committer of the EMF.cloud project](https://projects.eclipse.org/projects/ecd.emfcloud/who) will then review your contribution and help to get it merged.

Please note that before your pull request can be accepted, you must electronically sign the [Eclipse Contributor Agreement](https://www.eclipse.org/legal/ECA.php).
For more information, see the [Eclipse Foundation Project Handbook](https://www.eclipse.org/projects/handbook/#resources-commit).

### Branch names and commit messages

If you are an [elected committer of the EMF.cloud project](https://projects.eclipse.org/projects/ecd.emfcloud/who) please create a branch in the repository directly.
Otherwise please fork and create a branch in your fork for the pull request.

The branch name should be in the form `issues/{issue_number}`, e.g. `issues/16`. So please create an issue before creating a pull request.
All branches with this naming schema will be deleted after they are merged.

In the commit message you should also reference the corresponding issue, e.g. using `closes https://github.com/eclipse-emfcloud/coffee-editor/issues/16`, thus allowing [auto close of issues](https://help.github.com/en/github/managing-your-work-on-github/closing-issues-using-keywords).
Please use the absolute URL of the issue instead of just `#16`, as using the absolute URL will allow to correctly reference issues irrespectively from where you open the pull request.

Please make sure you read the [guide for a good commit message](https://chris.beams.io/posts/git-commit/).