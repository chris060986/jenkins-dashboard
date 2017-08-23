# Jenkins-Dashboard 

[![Build Status](https://travis-ci.org/chris060986/jenkins-dashboard.svg?branch=master)](https://travis-ci.org/chris060986/jenkins-dashboard)

A build dashboard for jenkins for all who are no allowed to install jenkins plugins.

## Motivation
Target users are **NOT** admin-users who setting up their own jenkins, for a single project and are free to do whatever they want with their jenkins installation. Thy should have a look for the [Jenkins Build Monitor](https://github.com/jan-molak/jenkins-build-monitor-plugin/).
This application is for users with no admin access to jenkins and with no possibility to install or update plugins, like in companies where build server is provided as a service for all development teams.

The Jenkins Dashboard does'nt claim to be a full monitor of all builds and history, that is provided by jenkins itself, but should give a quick and easy overview if all builds are passed. You should be able to get this information while you just walk by the dashboard.

## How to use
TODO

### Start demo with public jenkins
TODO

### Configuration
TODO

## Restrictions
App is maybe slow if much builds have to be load. Their is no caching or anything else at the moment.

## Features - not implemented yet

- Fixing layout
- Configuring URL in yaml file
- Configure which projects to see
- Making multiproject layout
- using SSL
- caching or no reloading for not changed builds
- use environmentvariables to make containerization possible

