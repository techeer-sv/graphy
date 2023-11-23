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

export type FilterType = {
  category: 'position' | 'skill' | 'keyword' | 'isRecruiting'
  name: PositionType | SkillType | string
}
