cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in REGSWIPEFragments REGSWIPEFragmentsAssets SWIPEDocumentOCRFragment SWIPEDocumentOCRFragmentAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack;do cd ../${pkg} && brazil-build clean; done
