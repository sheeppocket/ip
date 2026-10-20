# Remaining course checks

Implementation and published delivery are separate checks. Only mark an item
complete after verifying its evidence; local tags alone do not confirm that a
release, PR, or website is public.

## Delivery verification

- [ ] Verify the completed JavaFX GUI, all commands, save/reload, and failed-save
  rollback using the Java 25 build and the packaged JAR from an empty folder.
- [ ] Verify the three parallel PRs (`branch-A-Assertions`,
  `branch-A-CodeQuality`, `branch-A-Streams`) target this fork's `master`, were
  prepared before any was merged, and use merge commits. After each merge,
  pull `master`, tag its merge, then merge `master` into remaining branches,
  test and push before proceeding. Check the corresponding `A-*` tags remotely.
- [ ] Verify milestone tags against their actual changes, including `Level-10`,
  `BCD-Extension` (the `stats` feature, category C-Statistics), and
  `A-FullCommitMessage`. Audit the last five commit subjects against conventions.
- [ ] Verify `docs/Ui.png` shows the full real GUI window, and GitHub Pages serves
  `docs/README.md` from `master` + `/docs` with readable formatting and links.
- [ ] Verify the latest public GitHub release contains one Java 25 fat JAR asset;
  download and smoke-test that exact asset. The final submission consists of the
  published JAR, user guide, and code, with no separate final Canvas upload.

## Checks requiring the student's participation

- [ ] Confirm teammates chose different B/C/D extensions; this project uses
  **C-Statistics**. Adjust the choice if it duplicates a teammate's selection.
- [ ] Arrange a real smoke test on another operating system. Bundled libraries
  and automated checks cannot establish that someone tested that environment.
- [ ] Confirm the assigned Week 4 peer PR reviews and Week 8 Canvas evaluations
  were completed. Historical participation and submission dates cannot be
  recreated by making code changes now.
- [ ] Review and understand AI-assisted changes and confirm the course dashboard
  reflects the actual completed work, including the required weekly participation.

Sources: [Week 5](https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-w5.html),
[Week 6](https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-w6.html),
[Week 8](https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-w8.html), and
[grading](https://nus-cs2103-ay2627-s1.github.io/website/admin/ip-grading.html).
