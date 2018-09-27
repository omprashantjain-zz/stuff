cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in REGSWIPEFragmentsAssets SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLWebStack;do cd ../${pkg} && echo 'Building ${pkg}' && brazil-build; done
