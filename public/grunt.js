/* global module:false */
module.exports = function(grunt) {

  grunt.initConfig({

    meta: {},

    less: {
      production: {
        options: {
          paths: ["css"]
        },
        files: {
          "css/yawil.css": "css/yawil.less"
        }
      }
    },

    reload: {
      port: 6001,
      proxy: {
        host: 'localhost',
        port: 8000
      }
    },

    watch: {
      files: ['css/*.less', "*.html", "scripts/*.js"],
      tasks: 'less reload'
    }
  });
  
  grunt.loadNpmTasks( 'grunt-contrib-less' );
  grunt.loadNpmTasks( 'grunt-contrib-mincss' );
  grunt.loadNpmTasks( 'grunt-reload');
  grunt.registerTask( 'default', 'server reload watch');
};
