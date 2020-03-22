brazil-recursive-cmd --allPackages --reverse --continue brazil-build clean
brazil ws --sync
brazil-recursive-cmd --allPackages brazil-build release
