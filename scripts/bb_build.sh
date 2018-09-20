cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in REGSWIPEFragments REGSWIPEFragmentsAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack;do cd ../${pkg} && echo 'Building ${pkg}' && brazil-build; done
