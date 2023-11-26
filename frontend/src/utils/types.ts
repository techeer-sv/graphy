export const Position = [
  'FRONTEND',
  'BACKEND',
  'DESIGNER',
  'PM',
  'DEVOPS',
  'AI',
] as const

export const Skill = ['React', 'Spring', 'MongoDB', 'Redis', 'MySQL'] as const

export type PositionType = (typeof Position)[number]
export type SkillType = (typeof Skill)[number]

export type MultipleFilterType = {
  category: 'position' | 'skill'
  name: PositionType | SkillType
}
