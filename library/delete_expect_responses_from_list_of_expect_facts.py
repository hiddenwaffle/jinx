"""For when looping the expect module and registering the results, but the
module that receives the results can possibly fail and leak the responses
in its error output.
"""

from ansible.module_utils.basic import AnsibleModule


def remove_response(result):
    """Safely navigate the result in case it was not created by expect,
    and delete the nested key if found"""
    if 'invocation' not in result:
        return
    if 'module_args' not in result['invocation']:
        return
    if 'responses' not in result['invocation']['module_args']:
        return
    del result['invocation']['module_args']['responses']


def remove_responses(original):
    """A dictionary created by a loop will have a results key, otherwise
    the caller may have temporarily removed the loop"""
    if 'results' not in original:
        return
    for result in original['results']:
        remove_response(result)


def run_module():
    module_args = {
        'original': {
            'type': 'dict',
            'required': True
        }
    }
    module = AnsibleModule(argument_spec=module_args)
    remove_responses(module.params['original'])
    module.exit_json(**module.params)


def main():
    run_module()


if __name__ == '__main__':
    main()
