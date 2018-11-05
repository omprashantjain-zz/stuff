cd ~/workspaces/SWIPERTOL/src/REGSWIPEFragments
for pkg in  KycBusinessTypeFragment KycBusinessTypeFragmentAsset REGSWIPEFragments REGSWIPEFragmentsAssets SWIPEDocumentOCRFragment SWIPEDocumentOCRFragmentAssets SIVISFragment SIVISFragmentAssets SWIPERTOLLayouts SWIPERTOLWebStackAssets SWIPERTOLEndPointConfig SWIPERTOLWebStack; do
  cd ../${pkg}
  if git diff-index --quiet HEAD --; then
    git pull;
  else
    git stash;
    git pull;
    git stash pop;
  fi
done
