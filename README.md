# Play Grunt

Run Grunt on application start in Play dev mode using "grunt run".
Grunt will initially check and compile/minify css and javascript files in "grunt.home".
Grunt will check css and javascript files in "grunt.home" for changes in Play dev mode.
Run "grunt dist" from "grunt.home" in command line before deployment.
Set "grunt.home" in application.conf.
Toggle grunt output with "grunt.log" in application.conf using values of true or false.
Enable grunt with "grunt.enabled" in application.conf using values of true or false.
Configure Grunt using Gruntfile.js in "grunt.home".
