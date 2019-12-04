**Azure Server DevOps Server Plugin for Jenkins **

This plugin is a fork of https://github.com/jenkinsci/tfs-plugin

*This plugin was formerly known as TFS (team foundation server), a prior version of Azure DevOps Server*

What does it do? Why modify the original? Because it didn't work with the current release of Azure DevOps. The URLs for the API changed since 2018 and the plugin still referred to the old URLs. My edits reflect the new URLs, in order for it to work. Questions? Please email michael@michaelcgood.com

---

## To Build and Install

You’ll start by editing this README file to learn how to edit a file in Bitbucket.

1. change dir to the tfs-plugin folder
2. run "mvn package"
3. If you do not have Maven installed yet, please install it.
4. Initial build will have to download lots of libraries. This could take a few minutes.
5. This produces tfs-plugin\tfs\target\tfs.hpi
6. One way to install it is from the UI: Manage Jenkins -> Manage Plugins -> Advanced -> Upload Plugin - Choose tfs.hpi
7. Visit yourjenkinsurl:(port)/safeRestart and restart jenkins 

