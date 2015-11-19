var del = require('del'),
    argv = require('yargs').argv,
    gulp = require('gulp'),
    replace = require('gulp-replace'),
    minifyCss = require('gulp-minify-css'),
    rename = require('gulp-rename'),
    sass = require('gulp-sass'),
    browserify = require('gulp-browserify'),
    uglify = require('gulp-uglify'),
    concat = require('gulp-concat'),
    twig = require('gulp-twig');

var DEV_DOMAIN = 'localhost:9000',
    PROD_DOMAIN = 'play-seed.yangbajing.me';
var DOMAIN = DEV_DOMAIN;
var PUBLIC = '../public';

if (argv.prod || argv.test) {
    process.env.NODE_ENV = 'production';
    DOMAIN = PROD_DOMAIN;
}

/*********************************************************************
 * vendor
 ********************************************************************/
gulp.task('build:vendor:all', function () {
    return gulp.src(['src/vendor/**/*'], {base: 'src/vendor/'})
        .pipe(gulp.dest(PUBLIC + '/vendor'));
});
gulp.task('build:vendor', ['build:vendor:all']);
gulp.task('watch:vendor', function () {
    gulp.watch('src/vendor/**/*', ['build:vendor']);
});


/*********************************************************************
 * site
 *********************************************************************/
gulp.task('build:site:js', function () {
    return gulp.src('src/site/js/**/*.js')
        .pipe(replace('{{psDomain}}', DOMAIN))
        .pipe(browserify())
        .pipe(gulp.dest(PUBLIC + '/js/'));
});

//gulp.task('build:site:js:sign', function () {
//    return gulp.src('src/site/js/sign.js')
//        .pipe(browserify({transform: 'reactify'}))
//        .pipe(concat('sign.js'))
//        .pipe(gulp.dest(PUBLIC + '/js/'));
//});

gulp.task('build:site:css', function () {
    return gulp.src('src/site/scss/*.scss')
        .pipe(sass())
        .pipe(gulp.dest(PUBLIC + '/css'));
});

gulp.task('build:site:img', function () {
    return gulp.src('src/site/img/**/*', {base: 'src/site/'})
        .pipe(gulp.dest(PUBLIC + '/'));
});

gulp.task('build:site', [/*'build:site:js:site', 'build:site:js:sign', 'build:site:js:topic', 'build:site:js:discovery',*/ 'build:site:css', 'build:site:img', 'build:site:js']);

gulp.task('watch:site', function () {
    gulp.watch('src/site/**/*', ['build:site']);
});


/*********************************************************************
 * utility
 ********************************************************************/
gulp.task('clean', function (cb) {
    del([PUBLIC + '/**/*'], {force: true}, cb);
});

gulp.task('build', ['build:vendor', 'build:site'/*, 'build:site:views', 'build:app'*/]);

gulp.task('watch', ['watch:vendor', 'watch:site'/*, 'watch:site:views', 'watch:app'*/]);

gulp.task('min', function (cb) {
        gulp.src([
            PUBLIC + '/**/*.css'
        ], {base: PUBLIC + '/'})
            .pipe(minifyCss())
            .pipe(gulp.dest('build/'));

        gulp.src([
            PUBLIC + '/**/*.js'
        ], {base: PUBLIC + '/'})
            .pipe(uglify())
            .pipe(gulp.dest('build/'));
    }
);
