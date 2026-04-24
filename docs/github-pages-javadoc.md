# GitHub Pages (Javadoc)

This repository publishes **aggregate Javadoc** for all modules (`syntax`, `server`) via GitHub Actions.

## Workflow

- File: [`.github/workflows/deploy-javadoc.yml`](../.github/workflows/deploy-javadoc.yml)
- Triggers: pushes to **`main`** and **workflow_dispatch**
- Build: `mvn javadoc:aggregate` (Maven Javadoc Plugin **3.11.2**) → output **`target/reports/apidocs`**
- Deploy: [upload-pages-artifact](https://github.com/actions/upload-pages-artifact) + [deploy-pages](https://github.com/actions/deploy-pages)

An empty **`.nojekyll`** file is added next to `index.html` so GitHub Pages does not ignore paths that look like Jekyll partials (underscores in some Javadoc layouts).

## One-time repository setup

1. **Settings → Pages**  
   - **Build and deployment → Source:** **GitHub Actions** (not “Deploy from a branch”).

2. **Settings → Actions → General**  
   - Ensure workflows are allowed to run (default for public repos).

3. After the first successful run, open the workflow run summary or the **github-pages** environment URL. For a **project site** the Javadoc root is typically:

   `https://<owner>.github.io/<repository>/`

   (Example: `https://huauauaa.github.io/ai-java/` — replace owner and repo name.)

## Local preview

From the repository root:

```bash
mvn -B -DskipTests org.apache.maven.plugins:maven-javadoc-plugin:3.11.2:aggregate
```

Then open `target/reports/apidocs/index.html` in a browser.

---

[← Development guide](develop.md) · [← README](../README.md)
