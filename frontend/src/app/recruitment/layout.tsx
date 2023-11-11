import RecruitmentNavBar from '../../components/general/RecruitmentNavBar'

export default function ProjectLayout({
  children,
}: {
  children: React.ReactNode
}) {
  return <RecruitmentNavBar>{children}</RecruitmentNavBar>
}
