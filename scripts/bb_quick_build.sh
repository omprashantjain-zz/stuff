cd ~/workspaces/SWIPERTOL/src/SWIPERTOLWebStack
brazil workspace --sync --metadata
for pkg in REGSWIPEFragments REGSWIPEFragmentsAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack;do cd ../${pkg} && echo "Building $pkg" && brazil-build build; done
