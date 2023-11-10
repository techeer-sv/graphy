import ProjectNavBar from '../../components/general/ProjectNavBar'

export default function ProjectLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return <ProjectNavBar>{children}</ProjectNavBar>
}
