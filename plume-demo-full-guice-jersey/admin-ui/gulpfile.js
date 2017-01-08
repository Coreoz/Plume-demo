var gulp = require('gulp');
var concat = require('gulp-concat');
var gCheerio = require('gulp-cheerio');
var ngHtml2js = require("gulp-ng-html2js");
var htmlmin = require('gulp-htmlmin');
var streamqueue = require('streamqueue');
var rimraf = require('rimraf');
var domSrc = require('gulp-dom-src');
var rev = require('gulp-rev');
var revReplace = require('gulp-rev-replace');
var replace = require('gulp-replace');

//
// BUILD
//

var target = '../src/main/resources/statics/admin';
var source = '.';
var excludeFiles = ['!'+source+'/node_modules/**'];

var htmlminOptions = {
	collapseBooleanAttributes: true,
	collapseWhitespace: true,
	removeAttributeQuotes: false,
	removeComments: true,
	removeEmptyAttributes: true,
	removeRedundantAttributes: true,
	removeScriptTypeAttributes: true,
	removeStyleLinkTypeAttributes: true
};

gulp.task('clean', function() {
	rimraf.sync(target);
});

gulp.task('css', ['clean', 'sass'], function() {
	var cssStream = domSrc({file:source+'/index.html',selector:'link[rel="stylesheet"]',attribute:'href'});
	
	return cssStream
		.pipe(replace('../fonts/', 'fonts/'))
		.pipe(replace('app/img/', 'img/'))
		.pipe(concat('app.css'))
		.pipe(rev())
		.pipe(gulp.dest(target+'/'))
		.pipe(rev.manifest({merge:true}))
		.pipe(gulp.dest('.'));
});

gulp.task('js', ['clean'], function() {
	var templateStream = gulp
		.src(excludeFiles.concat(source + '/app/**/*.html'))
		.pipe(replace('app/img/', 'img/'))
		.pipe(htmlmin(htmlminOptions))
		.pipe(ngHtml2js({
			moduleName: "admin",
			prefix: "app/"
		}));

	var jsStream = domSrc({file:source+'/index.html',selector:'script[data-build!="exclude"]',attribute:'src'});
	
	var combined = streamqueue({ objectMode: true });

	combined.queue(jsStream);
	combined.queue(templateStream);

	return combined
		.done()
		.pipe(concat('app.js'))
		.pipe(rev())
		.pipe(gulp.dest(target+'/'))
		.pipe(rev.manifest({merge:true}))
		.pipe(gulp.dest('.'))
});

gulp.task('indexHtml', ['css', 'js'], function() {
	var manifest = gulp.src("rev-manifest.json");
	
	return gulp
		.src(source+'/index.html')
		.pipe(gCheerio(function ($) {
			$('script[data-build!="exclude"]').remove();
			$('link[rel="stylesheet"]').remove();
			$('head').append('<link rel="stylesheet" href="app.css"><script src="app.js"></script>');
		}))
		.pipe(revReplace({manifest: manifest}))
		.pipe(htmlmin(htmlminOptions))
		.pipe(replace('app/', ''))
		.pipe(gulp.dest(target+'/'));
});

gulp.task('copy', ['clean'], function(){
	gulp.src(source+'/app/app.const.js').pipe(gulp.dest(target+'/'));
	gulp.src(source+'/app/img/**').pipe(gulp.dest(target+'/img/'));
	gulp.src(source+'/node_modules/font-awesome/fonts/**').pipe(gulp.dest(target+'/fonts/'));
});

gulp.task('build', ['clean', 'css', 'js', 'indexHtml', 'copy']);

//
// DEV
//

var sass = require('gulp-sass');
var browserSync = require('browser-sync').create();
var watch = require('gulp-watch');

gulp.task('sass', function() {
	gulp
		.src('app/sass/**/*.scss')
		.pipe(sass().on('error', sass.logError))
		.pipe(gulp.dest('./app/css/'))
		.pipe(browserSync.stream());
});

gulp.task('serve', ['sass'], function () {
	browserSync.init({
		server: {
			baseDir: "./"
		}
	});

	watch('app/sass/**/*.scss', function() { gulp.start('sass'); });
	watch(["index.html", "app/**/*", "!app/css/*.css", "!app/sass/**/*.scss"]).on('change', browserSync.reload);
});

