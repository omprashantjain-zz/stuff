cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in  KycBusinessTypeFragment KycBusinessTypeFragmentAsset REGSWIPEFragments REGSWIPEFragmentsAssets SWIPEDocumentOCRFragment SWIPEDocumentOCRFragmentAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack;do cd ../${pkg} && brazil-build clean; done
