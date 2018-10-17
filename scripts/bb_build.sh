cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in REGSWIPEFragments REGSWIPEFragmentsAssets KycEntityFragment KycEntityFragmentAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack;do cd ../${pkg} && echo 'Building ${pkg}' && brazil-build; done
