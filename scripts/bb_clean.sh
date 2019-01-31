cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
#for pkg in REGSWIPEFragments REGSWIPEFragmentsAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack;do cd ../${pkg} && brazil-build clean; done
brazil workspace --sync --metadata
brazil-recursive-cmd --allPackages --reverse --continue brazil-build clean
brazil ws --sync
brazil-recursive-cmd --allPackages brazil-build release
